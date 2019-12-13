function disableMonitoring(id) {
    fetch(`/api/monitoring/disable/${id}`, {method: 'POST'})
        .then((response) => {
            if (response.status != 200) throw new Error('Monitoring was not disabled');
            else return response.text();
        })
        .catch(alert)
        .then(text => setStatus(id, text))
        .then(() => renderDisable(id))
}

function enableMonitoring(id) {
    fetch(`/api/monitoring/enable/${id}`, {method: 'POST'})
        .then((response) => {
            if (response.status != 200)
                throw new Error('Monitoring was not enable');
        })
        .catch(alert)
        .then(() => renderEnable(id))
}

function renderDisable(id) {
    let resume = document.getElementById(`resume_${id}`);
    let pause = document.getElementById(`pause_${id}`);
    let status = document.getElementById(`status_${id}`);
    resume.classList.replace("btn-secondary" ,"btn-success");
    pause.classList.replace("btn-success" ,"btn-secondary");
    resume.classList.toggle("disabled");
    pause.classList.toggle("disabled");
    status.classList.toggle("invisible");
    pause.disabled = true;
    resume.disabled = false;
}
function renderEnable(id) {
    let resume = document.getElementById(`resume_${id}`);
    let pause = document.getElementById(`pause_${id}`);
    let status = document.getElementById(`status_${id}`);
    resume.classList.replace("btn-success", "btn-secondary");
    pause.classList.replace("btn-secondary" ,"btn-success");
    resume.classList.toggle("disabled");
    pause.classList.toggle("disabled");
    status.classList.toggle("invisible");
    pause.disabled = false;
    resume.disabled = true;
}

function setStatus(id, text) {
    let status = document.querySelector(`#status_${id}>span`);
    // text = text.replaceAll('\"', '');
    status.innerText = text;
}




