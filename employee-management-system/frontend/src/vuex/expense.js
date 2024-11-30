import axios from "axios"
export default {
    namespaced : true,
    actions : {
        setFixedCharges(context,value){
            console.log("0000000000"+JSON.stringify(value));
            axios.post('http://localhost:8081/employee/expense/addFixedCharges', value)
                .then(response => {
                    console.log(response.data.message);
                    context.commit('getResponse', response.data);
                })
                .catch(error => {
                    console.log(error);
                });
        },
        setDailyExpense(context,value){
            axios.post('http://localhost:8081/employee/expense/addDailyExpense', value)
                .then(response => {
                    console.log(response.data.message);
                    context.commit('getResponse', response.data);
                })
                .catch(error => {
                    console.log(error);
                });
        },
        setTaxExpense(value){
            axios.post('http://localhost:8081/employee/expense/addTaxExpense', value)
                .then(response => {
                    console.log(response.data.message);
                })
                .catch(error => {
                    console.log(error);
                });
        },
    },
    mutations : {
        getResponse(state, value){
            state.isSuccess = value.result;
            state.dialogMessage = value.message;
        }
    },
    state : {
        isSuccess: false,
        dialogMessage: '',
    },
    getters : {}
}