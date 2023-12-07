document.addEventListener("DOMContentLoaded", function () {
    updateCounters();
})
let tasks = document.getElementsByClassName("task");
let boxes = [document.getElementById("todo"), document.getElementById("inprogres"), document.getElementById("done"), document.getElementById("canceled")];
let counters = [document.getElementById("ctr1"), document.getElementById("ctr2"), document.getElementById("ctr3"), document.getElementById("ctr4")];

for (let task of tasks) {
    task.addEventListener("dragstart", function (e) {
        let selected = e.target;
        selected.classList.add("dragging");
    });
}

for (let box of boxes) {
    box.addEventListener("dragover", function (e) {
        e.preventDefault();
    });

    box.addEventListener("drop", function (e) {
        let selected = document.querySelector(".dragging");
        selected.classList.remove("dragging");

        let currentBoxId = selected.closest(".box").id;
        let targetBoxId = box.id;

        if (currentBoxId !== targetBoxId) {
            box.appendChild(selected);
            updateCounters();

            let taskId = selected.querySelector(".id").textContent;
            let statusTask = determineNewStatus(box);
            updateTaskStatus(taskId, statusTask);
        }
    });
}

function updateCounters() {
    for (let i = 0; i < boxes.length; i++) {
        counters[i].textContent = boxes[i].getElementsByClassName("task").length;
    }
}

function determineNewStatus(dropTarget) {
    if (dropTarget.id === 'todo') {
        return 'TODO';
    } else if (dropTarget.id === 'inprogres') {
        return 'IN_PROGRESS';
    } else if (dropTarget.id === 'done') {
        return 'DONE';
    } else if (dropTarget.id === 'canceled') {
        return 'CANCELED';
    }
    return 'DEFAULT';
}

function updateTaskStatus(taskId, statusTask) {
    console.log(taskId + " " + statusTask);
    $.ajax({
        type: 'POST',
        url: '/updateTaskStatus',
        data: {
            taskId: taskId,
            StatusTask: statusTask
        }
    });
}