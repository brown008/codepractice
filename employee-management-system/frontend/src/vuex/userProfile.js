import axios from "axios"
export default {
    namespaced : true,
    actions : {
        getUserInfo(context, value){
            axios.get(`http://localhost:8081/employee/userInfo/getInfo?userId=${value.userid}&employeeId=${value.employeeid}`)
                .then(response => {
                    console.log(response.data);
                    context.commit('loadInfo', response.data);
                })
                .catch(error => {
                    console.log(error);
                });
        }
        
    },
    mutations : {
        loadInfo(state, value){
            state.user = value.user;
            state.employee = value.employee;
            state.salary = value.salary;
            state.address = value.address;
            state.employeeLogs = value.employeeLogsList;
        }
    },
    state : {
        user: {},
        employee: {},
        salary: {},
        address: {},
        employeeLogs: []
    },
    getters : {}
}