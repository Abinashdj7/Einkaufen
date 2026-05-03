import { applyMiddleware, combineReducers, legacy_createStore } from "redux";
import { thunk } from "redux-thunk";
import { adminOrderReducer, authReducer, cartReducer, customerProductReducer, orderReducer } from "./Reducer";

const rootReducers = combineReducers({
    auth: authReducer,
    product: customerProductReducer,
    cart: cartReducer,
    order: orderReducer,
    adminOrder: adminOrderReducer
})
export const store = legacy_createStore(rootReducers, applyMiddleware(thunk))