import axios from "axios"
export default {
    namespaced : true,
    actions : {
        getNewEmployee(context, value) {
            console.log(value);
            axios.post('http://localhost:8081/employee/employeeInfo/addEmployee', value)
                .then(response => {
                    console.log(response.data.message);
                    context.commit('getResponse', response.data);
                })
                .catch(error => {
                    console.log(error);
                });
        }
    },
    mutations : {
        getResponse(state, value){
            state.resultMessage = value.message;
        }
    },
    state : {
        resultMessage: ''
    },
    getters : {}
}