var path = require('path');

module.exports = {
    entry: {
        babel: 'babel-polyfill',
        main: ['./src/main/resources/js/app.js'],
        monitor: ['./src/main/resources/js/monitoringApp.js']},
    cache: true,
    mode: 'production',
    output: {
        path: __dirname,
        filename: './src/main/resources/static/built/[name].bundle.js'
    },
    module: {
        rules: [
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: ["@babel/preset-env", "@babel/preset-react"]
                    }
                }]
            }
        ]
    }
};