export async function getMonitorings() {
    return fetch(`/api/monitorings`)
        .then((response) => response.json());
}

export async function deleteMonitoring(id) {
    return fetch(`/api/monitorings/${id}`,
        {
            method: 'DELETE'
        });
}

