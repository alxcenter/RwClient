export async function resolveCaptcha(solved) {
    return fetch('/api/trainCaptcha',
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify({captcha: solved})
        })
        .then((response) => {
            if (response.status == 409) throw new Error('captcha');
            else return response.json();
        });
}

export async function getTrainList(moni) {
    return fetch(`/api/train`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(moni)
    })
        .then((response) => {
            if (response.status == 409) throw new Error('captcha');
            else return response.json();
        });
}

export async function  createMonitoring(moni) {
    return fetch(`/api/monitorings`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(moni)
    })
        .then((response) => {return response.json()});
}

