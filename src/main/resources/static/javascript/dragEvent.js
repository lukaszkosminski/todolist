document.addEventListener("DOMContentLoaded", function () {
    updateCounters();
})
let tasks = document.getElementsByClassName("task");
let boxes = [document.getElementById("TODO"), document.getElementById("IN_PROGRESS"), document.getElementById("DONE"), document.getElementById("CANCELED")];
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
            let targetBoxId = box.id;

            let taskId = selected.querySelector(".id").textContent;
            if (currentBoxId !== targetBoxId) {
                box.appendChild(selected);
                updateCounters();
                updateTaskStatus(taskId, box.id);
            }
        }
    });
}

function updateCounters() {
    for (let i = 0; i < boxes.length; i++) {
        counters[i].textContent = boxes[i].getElementsByClassName("task").length;
    }
}

function updateTaskStatus(idTask, statusTask) {
    console.log(idTask + " " + statusTask)
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: '/updateTaskStatus',
        data: JSON.stringify({
            idTask: idTask,
            statusTask: statusTask
        }),
    });
}