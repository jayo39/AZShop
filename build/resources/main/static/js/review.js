const stars = document.querySelectorAll(".star");
const selectedRatingInput = document.getElementById("selected-rating-input");

let currentRating = 0;

stars.forEach(star => {
  star.addEventListener("click", () => {
    const rating = star.getAttribute("data-rating");
    currentRating = rating;
    selectedRatingInput.value = rating;
    displayRating();
  });

  star.addEventListener("mouseover", () => {
    const rating = star.getAttribute("data-rating");
    highlightStars(rating);
  });

  star.addEventListener("mouseout", () => {
    highlightStars(currentRating);
  });
});

function displayRating(rating) {
  if (!rating) {
    rating = currentRating;
  }
}

function highlightStars(rating) {
  stars.forEach(star => {
    const starRating = star.getAttribute("data-rating");
    if (starRating <= rating) {
      star.classList.add("selected");
    } else {
      star.classList.remove("selected");
    }
  });
}

$(function() {
    const id = $("input[name='id']").val().trim();
    loadComment(id);

        $("#btn_review").click(function() {
            const content = $("#input_review").val().trim();
            const rating = $("#selected-rating-input").val();
            if(!rating) {
                alert("Select a rating.");
                $("#star-rating").focus();
                return;
            }
            if(!content) {
                alert("Write a short review.");
                $("#input_review").focus();
                return;
            }
            const data = {
                "user_id": logged_id,
                "product_id": id,
                "rating": rating,
                "content": content,
            };

            $.ajax({
                url: "/review/save",
                type: "POST",
                data: data,
                cache: false,
                success: function(data, status, xhr) {
                    if(status == "success") {
                        if(data.status != "OK") {
                            return;
                        }
                        loadComment(id);
                        $("#input_review").val('');
                    }
                },
            });
        });
});

function loadComment(product_id) {
    $.ajax({
        url: "/review/list?product_id=" + product_id,
        type: "GET",
        cache: false,
        success: function(data, status, xhr){
            if(status == "success") {
                if (data.status != "OK") {
                    alert(data.status);
                    return;
                }
                buildComment(data)
                addDelete();
            }
        }
    });
}

function buildComment(result) {
    $("#review_count").text(result.count);

    const out = [];
    let starTotal = 0;

    result.data.forEach(review => {
        let id = review.id;
        let content = review.content;
        let rating = review.rating;
        let reviewdate = review.reviewDate;
        let username = review.user.username;
        let user_id = review.user.id;
        starTotal += rating;

        const delBtn = (logged_id != user_id) ? '' : `
            <a class="btn btn-danger" data-reviewdel-id="${id}">Delete</a>
        `;

        const row = `
                    <tr>
                        <td><span><strong>${rating}</strong></span></td>
                        <td>
                            <span>${content}</span>
                        </td>
                        <td><span>${username}</span></td>
                        <td><span><small class="text-secondary">${reviewdate}</small></span></td>
                        <td style="width: 10%">${delBtn}</td>
                    </tr>
        `;

        out.push(row);
    });

    let starAvg = (starTotal / result.count);
    if(starAvg == 0 || starAvg == null || isNaN(starAvg)) {
        $("#reviewAvg").text("No Rating");
    } else {
        $("#reviewAvg").text(starAvg.toFixed(2));
    }

    $("#review_list").html(out.join("\n"));
}

function addDelete() {

    const id = $("input[name='id']").val().trim();

    $("[data-reviewdel-id]").click(function() {
        if(!confirm("Are you sure you want to delete your review?")) return;

        const review_id = $(this).attr("data-reviewdel-id");

        $.ajax({
            url: "/review/delete",
            type: "POST",
            cache: false,
            data: {"id": review_id},
            success: function(data, status, xhr) {
                if(status == "success"){
                    if(data.status != "OK") {
                        alert(data.status);
                        return;
                    }
                    loadComment(id);
                }
            },
        })
    });
}