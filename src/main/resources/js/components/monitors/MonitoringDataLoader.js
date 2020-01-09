export async function getMonitorings() {
    return fetch(`http://telega704.io/api/monitorings`)
        .then((response) => response.json());
}

export async function getDeleteMonitoring(id) {
    return fetch(`http://telega704.io/api/monitorings/${id}`)
        .then((response) => response.json());
}

