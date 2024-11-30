import Vue from "vue";
import Vuex from 'vuex';

Vue.use(Vuex)

import infoBean from "./infoBean";
import user from "./user";
import login from "./login";
import userProfile from "./userProfile";
import allEmployees from "./allEmployees";
import newEmployee from "./newEmployee";
import newContract from "./newContract";
import allRevenue from "./allRevenue";
import allContracts from "./allContracts";
import expense from "./expense";
export default new Vuex.Store({
    modules:{
        infoBean, 
        user, 
        login, 
        userProfile,
        allEmployees,
        allContracts,
        allRevenue,
        newEmployee,
        newContract,
        expense
    }
})