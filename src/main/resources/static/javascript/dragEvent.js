document.addEventListener("DOMContentLoaded", function () {
    updateCounters();
})
let tasks = document.querySelectorAll(".task");
let boxes = document.querySelectorAll(".box");
let counters = [document.getElementById("ctr1"), document.getElementById("ctr2"), document.getElementById("ctr3"), document.getElementById("ctr4")];

for (let task of tasks) {
    task.addEventListener("dragstart", function (e) {
        if(e.target !== undefined){
            let selected = e.target;
            if(selected.classList.contains("task") && typeof selected === "object"){
                selected.classList.add("dragging");
            }
        }
    });
}

for (let box of boxes) {
    box.addEventListener("dragover", function (e) {
        e.preventDefault();
    });

    box.addEventListener("drop", function () {
        let selected = document.querySelector(".dragging");
        if(selected != null) {
            selected.classList.remove("dragging");
            let currentBoxId = selected.closest(".box").id;

            let taskId = selected.querySelector(".id").textContent;
            if (currentBoxId !== box.id) {
                box.insertBefore(selected, box.querySelector(".addtask"));
                let formData = {
                    description: selected.querySelector(".description").textContent,
                    statusTask: box.id,
                    title: selected.querySelector(".title").textContent,
                    priorityTask: selected.id,
                }
                fetch(`http://localhost:8095/api/user/edit-task/${taskId}?idTaskCollection=${currentList.id}`, {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(formData)
                }).then(response => response.json()).then(() => {
                    updateCounters();
                })
            }
        }
    });
}

function updateCounters() {
    for (let i = 0; i < boxes.length; i++) {
        counters[i].textContent = boxes[i].getElementsByClassName("task").length;
    }
}