import axios from "axios"
export default {
    namespaced : true,
    actions : {
        getUserInfo(context, value){
            axios.get(`http://localhost:8081/employee/userInfo/getInfo?userId=${value.userid}&employeeId=${value.employeeid}`)
                .then(response => {
                    console.log("@@@@@@@@@@" + response.data);
                    context.commit('loadInfo', response.data);
                })
                .catch(error => {
                    console.log(error);
                });
        },
        updateUserProfile(context, value){
            console.log(value);
            axios.post('http://localhost:8081/employee/userInfo/updateInfo', value)
                .then(response => {
                    console.log("@@@@@@@@@@" + response.data);
                    // context.commit('updatedInfo', response.data);
                })
                .catch(error => {
                    console.log(error);
                });
        },
        getEmployeeInfo(context, value){
            axios.get(`http://localhost:8081/employee/employeeInfo/getEmployee?employeeId=${value.employeeid}`)
                .then(response => {
                    console.log("@@@@@@@@@@" + response.data);
                    context.commit('loadInfo', response.data);
                })
                .catch(error => {
                    console.log(error);
                });
        },
    },
    mutations : {
        loadInfo(state, value){
            state.user = value.user;
            state.employee = value.employee;
            state.salary = value.salary;
            state.address = value.address;
            state.employeeLogs = value.employeeLogs;
            state.downLineList = value.downLineList;
        }
    },
    state : {
        user: {},
        employee: {},
        salary: {},
        address: {},
        employeeLogs: {},
        downLineList: [],
        info: {
            user: {},
            employee: {},
            salary: {},
            address: {},
            employeeLogs: {},
        },
        personalLabel: {
            first: "",
            second: "",
            third: ""
        }
    },
    getters : {}
}