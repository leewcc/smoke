var webpack = require('webpack');
var path = require('path');
var htmlWebpackPlugin = require('html-webpack-plugin');
var ExtractTextPlugin = require('extract-text-webpack-plugin');

module.exports = {
    entry: {
        loading: path.resolve(__dirname, './src/main.js'),
        vendors: ['vue', 'vue-router']
    },
    output: {
        path:  path.resolve(__dirname,'./dist/js'),
        filename: '[name].js'
    },
    module: {
        loaders: [
        {
            test: /\.css$/,
            loader: 'css-loader',
            exclude: /node_modules/ 
        }, 
        { 
            test: /\.vue$/, 
            loader: 'vue-loader',
            exclude: /node_modules/ 
          
        },{
            test: /\.js$/,
            loader: 'babel',
            exclude: /node_modules/
        }
        ]
    },
    resolve: {
        extensions: ['', '.js', '.vue']
    },
    babel: {
        presets: ['es2015'],
        plugins: ['transform-runtime']
    },
    vue: { // vue的配置
        loaders: {
          js: 'babel',
          css: ExtractTextPlugin.extract('vue-style-loader', 'css-loader')
        }
      },
    plugins: [
        new webpack.DefinePlugin({
            'process.env': {
                NODE_ENV: '"production"'
            }
        }),
        new htmlWebpackPlugin({
            filename: path.resolve(__dirname, 'src/index.html'),
            template: path.resolve(__dirname, 'index.html'),
            chunks: ['vendors'],
            inject: 'body',
            title:'鹦鹉'
            }),
        new ExtractTextPlugin( "bundle.css" )
    ]
};
