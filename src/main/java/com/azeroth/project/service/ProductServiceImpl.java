package com.azeroth.project.service;

import com.azeroth.project.domain.ProductDomain;
import com.azeroth.project.repository.ProductRepository;
import com.azeroth.project.util.U;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Value("${app.upload.path}")
    private String uploadDir;
    @Value("${app.pagination.write_pages}")
    private int WRITE_PAGES;
    @Value("${app.pagination.page_rows}")
    private int PAGE_ROWS;

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(SqlSession sqlSession) {
        productRepository = sqlSession.getMapper(ProductRepository.class);
    }

    @Override
    public int addProduct(ProductDomain productDomain, MultipartFile file) {
        return upload(productDomain, file);
    }

    @Override
    public List<ProductDomain> listByPagination(Integer page, Model model) {
        if(page == null) page = 1;  // 디폴트는 1 page
        if(page < 1) page = 1;

        HttpSession session = U.getSession();
        Integer writePages = (Integer)session.getAttribute("writePages");
        if(writePages == null) writePages = WRITE_PAGES;
        Integer pageRows = (Integer)session.getAttribute("pageRows");
        if(pageRows == null) pageRows = PAGE_ROWS;

        session.setAttribute("page", page);

        long cnt = productRepository.countAll();
        if (cnt == 0) cnt = 1;
        int totalPage =  (int)Math.ceil(cnt / (double)pageRows);

        if(page > totalPage) page = totalPage;

        int fromRow = (page - 1) * pageRows;

        int startPage = (((page - 1) / writePages) * writePages) + 1;
        int endPage = startPage + writePages - 1;
        if (endPage >= totalPage) endPage = totalPage;

        model.addAttribute("cnt", cnt);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("pageRows", pageRows);

        model.addAttribute("url", U.getRequest().getRequestURI());
        model.addAttribute("writePages", writePages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        List<ProductDomain> list = productRepository.selectFromRow(fromRow, pageRows);
        model.addAttribute("list", list);

        return list;
    }

    @Override
    public List<ProductDomain> listByCategory(String maincode, String subcode) {
        return productRepository.listByCategory(maincode, subcode);
    }

    @Override
    public List<ProductDomain> listBySearch(String searchedValue) {
        return productRepository.listBySearch(searchedValue);
    }

    @Override
    public ProductDomain findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public int delete(Long id, String originalImage) {
        delFile(originalImage);
        return productRepository.delete(id);
    }

    @Override
    public int update(Integer isDelete, String originalImage, ProductDomain productDomain, MultipartFile file) {
        if(isDelete == 1) {
            delFile(originalImage);
            productDomain.setP_img(null);
        } else if (isDelete == 0) {
            delFile(originalImage);
            String originalFilename = file.getOriginalFilename();

            if (originalFilename == null || originalFilename.length() == 0) {
                productDomain.setP_img(null);
                return productRepository.update(productDomain);
            }
            // 원본파일명
            String sourceName = StringUtils.cleanPath(originalFilename);

            // 저장될 파일명
            String fileName = sourceName;

            // 파일이 중복되는지 확인
            File file1 = new File(uploadDir + File.separator + sourceName);
            if (file1.exists()) {
                int pos = fileName.lastIndexOf(".");
                if (pos > -1) {
                    String name = fileName.substring(0, pos);
                    String ext = fileName.substring(pos + 1);

                    fileName = name + "_" + System.currentTimeMillis() + "." + ext;
                } else {
                    fileName += "_" + System.currentTimeMillis();
                }
            }

            productDomain.setP_img(fileName);

            // java.nio.*
            Path copyOfLocation = Paths.get(new File(uploadDir + File.separator + fileName).getAbsolutePath());

            try {
                Files.copy(
                        file.getInputStream(),
                        copyOfLocation,
                        StandardCopyOption.REPLACE_EXISTING
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
            return productRepository.update(productDomain);
        }

        return productRepository.update(productDomain);
    }

    @Override
    public int modifyAmount(Long product_id, Long amount) {
        return productRepository.modifyAmount(product_id, amount);
    }

    private void delFile(String originalImage) {
        String saveDirectory = new File(uploadDir).getAbsolutePath();

        File f = new File(saveDirectory, originalImage);
        f.delete();
    }


    private int upload(ProductDomain productDomain, MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        if(productDomain.getStock() == null) {
            productDomain.setStock(0L);
        }
        if (originalFilename == null || originalFilename.length() == 0) {
            productDomain.setP_img(null);
            return productRepository.addProduct(productDomain);
        }
        // 원본파일명
        String sourceName = StringUtils.cleanPath(originalFilename);

        // 저장될 파일명
        String fileName = sourceName;

        // 파일이 중복되는지 확인
        File file = new File(uploadDir + File.separator + sourceName);
        if (file.exists()) {
            int pos = fileName.lastIndexOf(".");
            if (pos > -1) {
                String name = fileName.substring(0, pos);
                String ext = fileName.substring(pos + 1);

                fileName = name + "_" + System.currentTimeMillis() + "." + ext;
            } else {
                fileName += "_" + System.currentTimeMillis();
            }
        }

        productDomain.setP_img(fileName);

        // java.nio.*
        Path copyOfLocation = Paths.get(new File(uploadDir + File.separator + fileName).getAbsolutePath());

        try {
            Files.copy(
                    multipartFile.getInputStream(),
                    copyOfLocation,
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productRepository.addProduct(productDomain);
    }
}
