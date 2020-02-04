export class CaptchaEx extends Error {
    constructor(...args) {
        super(...args);
        Error.captureStackTrace(this, CaptchaEx);
    }
}