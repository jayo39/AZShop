var count = 0;

$(function() {

    const user_id = $("input[name='userid']").val().trim();
    loadAddress(user_id);

    $("#addressSave").click(function() {
        const addressName = $("#addressName").val().trim();
        const postcode = $("#postcode").val().trim();
        const address = $("#deliveryAddress").val().trim();
        const addressDetail = $("#deliveryDetail").val().trim();
        if(!addressName) {
            alert("주소 명을 입력해주세요.")
            $("#addressName").focus();
            return;
        }
        if(!postcode) {
            alert("우편번호를 입력해주세요.")
            $("#postcode").focus();
            return;
        }
        if(!address) {
            alert("주소를 입력해주세요.")
            $("#deliveryAddress").focus();
            return;
        }
        if(!addressDetail) {
            alert("상세주소를 입력해주세요.")
            $("#deliveryDetail").focus();
            return;
        }

        const data = {
            "user_id": user_id,
            "name": addressName,
            "address": address,
            "address_detail": addressDetail,
            "postcode": postcode,
        };

        $.ajax({
            url: "/user/address/save",
            type: "POST",
            data: data,
            cache: false,
            success: function(data, status, xhr) {
                if(status == "success") {
                    $("#addressName").val('');
                    $("#postcode").val('');
                    $("#deliveryAddress").val('');
                    $("#deliveryDetail").val('');
                    loadAddress(user_id);
                }
            },
        });
    });
});



function loadAddress(user_id) {
    $.ajax({
        url: "/user/addresslist?user_id=" + user_id,
        type: "GET",
        cache: false,
        success: function(data, status, xhr){
            if(status == "success") {
                $("#nomore").text('');
                $("#addressSave").removeAttr('disabled');
                buildAddress(data);
                if (data.data.length == 3) {
                    $("#addressSave").attr('disabled', 'disabled');
                    $("#nomore").text("주소는 3개까지만 등록 가능합니다.");
                }
                addDelete();
            }
        }
    });
}

function buildAddress(result) {
    const out = [];
    result.data.forEach(address => {
        let id = address.id;
        let name = address.name;
        let addressName = address.address
        let address_detail = address.address_detail;
        let postcode = address.postcode;
        const delBtn = `<a class="btn btn-danger" data-addressdel-id="${id}">삭제</a>`;

        const row = `
            <div class="card mb-3">
                <div class="card-body">
                    <div class="form-group">
                        <label for="addressName">주소 명</label>
                        <input type="text" class="form-control" value="${name}" readonly>
                    </div>
                    <div class="form-group">
                        <label for="postcode">우편번호</label>
                        <div class="row">
                            <div class="col-4">
                                <input type="text" class="form-control" value="${postcode}" readonly>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="deliveryAddress">주소</label>
                        <input type="text" class="form-control" value="${addressName}" readonly>
                    </div>
                    <div class="form-group">
                        <label for="deliveryDetail">상세주소</label>
                        <input type="text" class="form-control" value="${address_detail}" readonly>
                    </div>
                    <div class="d-flex justify-content-center mt-2">
                        ${delBtn}
                    </div>
            </div>
        </div>
        `;
        out.push(row);
    });
    $("#addressList").html(out.join("\n"));
}

function addDelete() {
    const user_id = $("input[name='userid']").val().trim();

    $("[data-addressdel-id]").click(function() {
        if(!confirm("주소를 삭제하시겠습니까?")) return;

        const address_id = $(this).attr("data-addressdel-id");
        console.log(address_id);

        $.ajax({
            url: "/user/address/delete",
            type: "POST",
            cache: false,
            data: {"id": address_id},
            success: function(data, status, xhr) {
                if(status == "success") {
                    loadAddress(user_id);
                }
            }
        });
    })
}