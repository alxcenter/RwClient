export async function getMonitorings() {
    return fetch(`http://telega704.io/api/monitorings`)
        .then((response) => response.json());
}

export async function deleteMonitoring(id) {
    return fetch(`http://telega704.io/api/monitorings/${id}`,
        {
            method: 'DELETE'
        });
}

