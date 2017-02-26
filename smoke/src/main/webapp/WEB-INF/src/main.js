import Vue from 'vue'
import VueRouter from 'vue-router'
import Hello from './components/Hello'
import Device from './components/Devices'
import Room from './components/Rooms'
import History from './components/History'
import Login from './components/Login'
import Manage from './components/Manage'
import Updata from './components/Updata'

Vue.use(VueRouter);
const router = new VueRouter({
	mode: 'history',
	routes :[{
		path: '/login',
		component: Login,
		name: 'login'
	},{
		path: '/manage',
		component: Manage,
		name: 'manage'
	},{
		path: '/updata',
		name: 'updata',
		component: Updata
	},{
		path : '/',
		component: Login,
		name: 'login'
	},{
		path : '/index',
		component: Room
	},{
		path : '/history',
		name: 'history',
		props: ['roomId'],
		component: History
	},{
		path : '/room',
		component: Room
	},{
		path: '/hello',
		component: Hello
	},{
		path: '/device',
		name: 'device',
		component: Device
	}
	]
});

var vue = new Vue({
	router: router

}).$mount('#app');
