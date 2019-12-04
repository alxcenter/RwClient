import React from 'react';
import TelegramLoginButton from 'react-telegram-login';

const handleTelegramResponse = response => {
    console.log(response);
};


export default function TelegramLogin(){
    return (
        <div className="telegramAuth">
            <TelegramLoginButton dataOnauth={handleTelegramResponse} botName="loclalhost_bot"/>
        </div>
    );
}