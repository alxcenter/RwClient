import {CaptchaEx} from "../../exceptions/CaptchaEx"

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
            if (response.ok) return response.json();
            else if (response.status == 409) {
                return response.json().then((obj) => {
                    console.log(obj.message);
                    throw new CaptchaEx(obj.message.toString());
                });
            }
            else {
                return response.json().then((obj) => {
                    console.log(obj.message);
                    throw new Error(obj.message.toString());
                });
            }
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
            if (response.ok) return response.json();
            else if (response.status == 409) {
                return response.json().then((obj) => {
                    console.log(obj.message);
                    throw new CaptchaEx(obj.message.toString());
                });
            }
            else {
                return response.json().then((obj) => {
                    console.log(obj.message);
                    throw new Error(obj.message.toString());
                });
            }
        });
}

export async function createMonitoring(moni) {
    return fetch(`/api/monitorings`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(moni)
    })
        .then((response) => {
            return response.json()
        });
}

