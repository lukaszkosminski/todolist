let addButton = document.querySelector(".btn");
let boxses = document.querySelectorAll(".box")
addButton.addEventListener("click", () => {
    for(let box of boxses){
        box.style.filter = "blur(2px)";
    }
    let content = document.querySelector(".content");
        let contentdiv = document.createElement("div");
        contentdiv.classList.add("contentdiv");
            let form = document.createElement("form");
            form.classList.add("form");
            form.appendChild(Input("Title"));
            form.appendChild(Input("Description"));
            form.appendChild(RadioInputs(["TODO", "IN_PROGRESS", "DONE", "CANCELED"], "StatusTask"));
            form.appendChild(RadioInputs(["LOW", "MEDIUM", "HIGH"], "PriorityTask"));
                let submitButton = document.createElement("input");
                submitButton.setAttribute("type", "submit");
                submitButton.setAttribute("value", "Add Task");
            form.appendChild(submitButton);
        contentdiv.appendChild(form);
    content.appendChild(contentdiv);

    document.querySelector('.form').addEventListener("submit", function (event) {
        event.preventDefault();
        for(let box of boxses){
            box.style.filter = "blur(0)";
        }
        let priorityTask = CheckedRadio("PriorityTask");
        let statusTask = CheckedRadio("StatusTask");
        let formData = {
            priorityTask: priorityTask,
            statusTask: statusTask,
            title: document.querySelector('input[name = "Title"]').value,
            description: document.querySelector('input[name = "Description"]').value,
        };
        fetch(`http://localhost:8080/api/user/create-task?idTaskCollection=${currentList.id}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(formData)
        }).then(response => response.json()).then(data => {
            window.location.href = `/list/${currentList.id}`;
        })
    });
    document.addEventListener("click", function (event) {
        if (!contentdiv.contains(event.target) && event.target !== addButton) {
            if (content.contains(contentdiv)) {
                for(let box of boxses){
                    box.style.filter = "blur(0)";
                }
                content.removeChild(contentdiv);
            }
        }
    });
});

function CheckedRadio(nameName) {
    const radioButtons = document.querySelectorAll('input[name=' + nameName + ']');
    for (let i of radioButtons) {
        if (i.checked) {
            return i.value;
        }
    }
}

function RadioInputs(statusTask, name){
    let element = document.createElement("div");
    element.classList.add("RadioInput");
    for (let i of statusTask) {
        let elements = document.createElement("div");
        elements.classList.add("element");
        elements.appendChild(RadioInput(i, name));
        elements.appendChild(RadioLabel(i, name));
        element.appendChild(elements);
    }
    return element;
}

function Input(name){
    let element = document.createElement("div");
    element.classList.add(name);
    element.appendChild(TextInput(name));
    element.appendChild(TextLabel(name));
    return element;
}

function TextInput(name){
    let textInput = document.createElement("input");
    textInput.setAttribute("type", "text");
    textInput.setAttribute("required", "");
    textInput.setAttribute("name", name);
    textInput.classList.add("input");
    return textInput;
}

function TextLabel(name){
    let textLabel = document.createElement("label");
    textLabel.classList.add("label" + name);
    textLabel.innerText = name;
    return textLabel;
}

function RadioInput(value, name){
    let statusTaskInput = document.createElement("input");
    statusTaskInput.setAttribute("type", "radio");
    statusTaskInput.setAttribute("name", name);
    statusTaskInput.setAttribute("value", value);
    statusTaskInput.setAttribute("required", true);
    return statusTaskInput;
}

function RadioLabel(statusTask, name){
    let statusTaskLabel = document.createElement("label");
    statusTaskLabel.classList.add("label" + name);
    statusTaskLabel.innerText = statusTask;
    return statusTaskLabel
}