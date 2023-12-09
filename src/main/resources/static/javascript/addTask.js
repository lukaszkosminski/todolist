
let addtaskElements = document.querySelectorAll(".addtask");
addtaskElements.forEach(addtaskElement => {
    addtaskElement.addEventListener("click", () => {
        addtaskElement.removeChild(addtaskElement.querySelector(".plus"))
        let form = document.createElement("form");
        form.setAttribute("action", "/list");
        form.setAttribute("method", "GET");
        form.style.width = "100%";

        let taskInput1 = document.createElement("div");
        taskInput1.classList.add("taskinput");
        taskInput1.style.marginLeft = "2%";
        taskInput1.style.width = "60%";
        taskInput1.style.height = "25px";
        taskInput1.style.float = "left";

        let inputTitle = document.createElement("input");
        inputTitle.setAttribute("type", "text");
        inputTitle.classList.add("title");
        inputTitle.classList.add("input");
        inputTitle.setAttribute("required", "");
        taskInput1.appendChild(inputTitle);
        let labelTitle = document.createElement("label");
        labelTitle.innerText = "Title";
        labelTitle.classList.add("label");
        labelTitle.style.fontSize = "15px";
        labelTitle.style.top = "-5px";
        taskInput1.appendChild(labelTitle);

        form.appendChild(taskInput1);

        let submitButton = document.createElement("input");
        submitButton.setAttribute("type", "submit");
        submitButton.setAttribute("value", "Add Task");
        submitButton.style.marginRight = "2%";
        submitButton.style.float = "right";
        submitButton.style.width = "30%";
        submitButton.style.height = "25px";
        form.appendChild(submitButton);

        let taskInput2 = document.createElement("div");
        taskInput2.classList.add("taskinput");
        taskInput2.style.margin = "1% 3% 0 3%";
        taskInput2.style.width = "96%";
        taskInput2.style.height = "30px";
        taskInput2.style.clear = "both";
        let inputDescription = document.createElement("input");
        inputDescription.setAttribute("type", "text");
        inputDescription.classList.add("description");
        inputDescription.classList.add("input");
        inputDescription.setAttribute("required", "");
        taskInput2.appendChild(inputDescription);
        let labelDescription = document.createElement("label");
        labelDescription.innerText = "Description";
        labelDescription.classList.add("label");
        labelDescription.style.fontSize = "15px";
        labelDescription.style.top = "-2px";
        taskInput2.appendChild(labelDescription);
        form.appendChild(taskInput2);
        addtaskElement.appendChild(form);

    });
})