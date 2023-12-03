import Vue from "vue";
import Vuex from 'vuex';

Vue.use(Vuex)

import infoBean from "./infoBean";
import user from "./user";
import login from "./login";
import userProfile from "./userProfile";
import allEmployees from "./allEmployees";
export default new Vuex.Store({
    modules:{
        infoBean, 
        user, 
        login, 
        userProfile,
        allEmployees
    }
})