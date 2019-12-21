export async function resolveCaptcha(solved) {
    return fetch('http://telega704.io/api/trainCaptcha',
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

export async function getDataAction(moni) {
    return fetch(`http://telega704.io/api/train`, {
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