import axios from "axios"
import { API_BASE_URL, api } from "./ApiConfig"
import { ADD_ITEM_TO_CART_FAILURE, ADD_ITEM_TO_CART_REQUEST, ADD_ITEM_TO_CART_SUCCESS, CHANGE_NUMBER_FAILURE, CHANGE_NUMBER_REQUEST, CHANGE_NUMBER_SUCCESS, CREATE_ORDER_FAILURE, CREATE_ORDER_REQUEST, CREATE_ORDER_SUCCESS, CREATE_PAYMENT_FAILURE, CREATE_PAYMENT_REQUEST, CREATE_PRODUCT_FAILURE, CREATE_PRODUCT_REQUEST, CREATE_PRODUCT_SUCCESS, DELETE_PRODUCT_FAILURE, DELETE_PRODUCT_REQUEST, DELETE_PRODUCT_SUCCESS, FIND_PRODUCTS_FAILURE, FIND_PRODUCTS_REQUEST, FIND_PRODUCTS_SUCCESS, FIND_PRODUCT_BY_ID_FAILURE, FIND_PRODUCT_BY_ID_REQUEST, FIND_PRODUCT_BY_ID_SUCCESS, GET_ALL_PRODUCTS_FAILURE, GET_ALL_PRODUCTS_REQUEST, GET_ALL_PRODUCTS_SUCCESS, GET_CART_FAILURE, GET_CART_REQUEST, GET_CART_SUCCESS, GET_ORDER_BY_ID_FAILURE, GET_ORDER_BY_ID_REQUEST, GET_ORDER_BY_ID_SUCCESS, GET_ORDER_HISTORY_FAILURE, GET_ORDER_HISTORY_REQUEST, GET_ORDER_HISTORY_SUCCESS, GET_USER_FAILURE, GET_USER_REQUEST, GET_USER_SUCCESS, LOGIN_FAILURE, LOGIN_REQUEST, LOGIN_SUCCESS, LOGOUT, REGISTER_FAILURE, REGISTER_REQUEST, REGISTER_SUCCESS, REMOVE_CART_ITEM_FAILURE, REMOVE_CART_ITEM_REQUEST, REMOVE_CART_ITEM_SUCCESS, UPDATE_CART_ITEM_REQUEST, UPDATE_PAYMENT_FAILURE, UPDATE_PAYMENT_REQUEST } from "./ActionType"
import { Dispatch } from "redux"
import { CreateNewFolderOutlined } from "@mui/icons-material"
import { canceledOrderFailure, canceledOrderRequest, canceledOrderSuccess, confirmedOrderFailure, confirmedOrderRequest, confirmedOrderSuccess, deleteOrderFailure, deleteOrderRequest, deleteOrderSuccess, deliveredOrderFailure, deliveredOrderRequest, deliveredOrderSuccess, getOrdersFailure, getOrdersRequest, getOrdersSuccess, shipOrderFailure, shipOrderRequest, shipOrderSuccess } from "./ActionCreator"

const registerRequest = () => ({ type: REGISTER_REQUEST })
const registerSuccess = (user) => ({ type: REGISTER_SUCCESS, payload: user })
const registerFailure = (error) => ({ type: REGISTER_FAILURE, payload: error })


export const register = (userData) => async (dispatch) => {
    dispatch(registerRequest())
    try {
        const response = await axios.post(`${API_BASE_URL}/api/auth/signup`, userData)
        const user = response.data
        if (user.jwt) {
            localStorage.setItem("jwt", user.jwt)
        }
        dispatch(registerSuccess())
    } catch (error) {
        dispatch(registerFailure(error.message))
    }
}

const loginRequest = () => ({ type: LOGIN_REQUEST })
const loginSuccess = (user) => ({ type: LOGIN_SUCCESS, payload: user })
const loginFailure = (error) => ({ type: LOGIN_FAILURE, payload: error })

export const loginUser = (userData) => async (dispatch) => {
    dispatch(loginRequest())
    try {
        const response = await axios.post(`${API_BASE_URL}/api/auth/login`, userData)
        const user = response.data
        if (user.jwt) {
            localStorage.setItem("jwt", user.jwt)
        }
        dispatch(loginSuccess(user.jwt))
    } catch (error) {
        dispatch(loginFailure(error.message))
    }
}

const getUserRequest = () => ({ type: GET_USER_REQUEST })
const getUserSuccess = (user) => ({ type: GET_USER_SUCCESS, payload: user })
const getUserFailure = (error) => ({ type: GET_USER_FAILURE, payload: error })

export const getUser = (jwt) => async (dispatch) => {
    dispatch(getUserRequest())
    try {
        const response = await axios.get(`${API_BASE_URL}/api/users/profile`, {
            headers: {
                "Authorization": `Bearer ${jwt}`

            }
        })
        const user = response.data
        dispatch(getUserSuccess(user))
    } catch (error) {
        dispatch(getUserFailure(error.message))
    }
}

export const logout = () => (dispatch) => {
    dispatch({ type: LOGOUT, payload: null })
    localStorage.removeItem("jwt")
}

export const findProducts = (reqData) => async (dispatch) => {
    const { colors, sizes, minPrice, maxPrice, minDiscount, category, stock, sort, pageNumber, pageSize } = reqData

    dispatch({ type: FIND_PRODUCTS_REQUEST })
    try {

        const { data } = await api.get(`/api/products?colors=${colors}&sizes=${sizes}&minPrice=${minPrice}&maxPrice=${maxPrice}&minDiscount=${minDiscount}&category=${category}&stock=${stock}&sort=${sort}&pageNumber=${pageNumber}&pageSize=${pageSize}`)
        console.log("Data - ", data)
        dispatch({ type: FIND_PRODUCTS_SUCCESS, payload: data })
    } catch (err) {
        dispatch({ type: FIND_PRODUCTS_FAILURE, payload: err.message })
    }
}
export const findProductsById = (reqData) => async (dispatch) => {

    dispatch({ type: FIND_PRODUCT_BY_ID_REQUEST })
    try {

        const { data } = await api.get(`/api/products/id/${reqData.productId}`)
        dispatch({ type: FIND_PRODUCT_BY_ID_SUCCESS, payload: data })
    } catch (err) {
        dispatch({ type: FIND_PRODUCT_BY_ID_FAILURE, payload: err.message })
    }
}

export const getAllProducts = () => async (dispatch) => {
    dispatch({ type: GET_ALL_PRODUCTS_REQUEST })
    try {
        const { data } = await api.get('/api/allProducts')
        dispatch({ type: GET_ALL_PRODUCTS_SUCCESS, payload: data })
    } catch (error) {
        dispatch({ type: GET_ALL_PRODUCTS_FAILURE, payload: error.message })
    }
}
export const getProductsByCategory = (reqData) => async (dispatch) => {
    dispatch({ type: GET_ALL_PRODUCTS_REQUEST })
    try {
        const { data } = await api.get(`/api/category/${reqData.firstLevel}/${reqData.secondLevel}/${reqData.thirdLevel}`)
        console.log(data)
        dispatch({ type: GET_ALL_PRODUCTS_SUCCESS, payload: data })
    } catch (error) {
        dispatch({ type: GET_ALL_PRODUCTS_FAILURE, payload: error.message })
    }
}
export const getCart = (jwt) => async (dispatch) => {
    dispatch({ type: GET_CART_REQUEST })
    try {
        const { data } = await api.get("/api/cart/", {
            headers: {
                "Authorization": `Bearer ${jwt}`
            }
        })
        dispatch({ type: GET_CART_SUCCESS, payload: data })
    } catch (error) {
        dispatch({ type: GET_CART_FAILURE, payload: error.message })
    }
}

export const addItemToCart = (reqData) => async (dispatch) => {
    dispatch({ type: ADD_ITEM_TO_CART_REQUEST })

    try {
        const { data } = await api.put("/api/cart/add", reqData)
        dispatch({ type: ADD_ITEM_TO_CART_SUCCESS, payload: data })
    } catch (error) {
        dispatch({ type: ADD_ITEM_TO_CART_FAILURE, payload: error.message })
    }
}

export const removeItemFromCart = (reqData) => async (dispatch) => {
    dispatch({ type: REMOVE_CART_ITEM_REQUEST })

    try {
        const { data } = await api.delete(`/api/cartItem/delete/${reqData}`)
        dispatch({ type: REMOVE_CART_ITEM_SUCCESS, payload: data })
    } catch (error) {
        dispatch({ type: REMOVE_CART_ITEM_FAILURE, payload: error.message })
    }
}

export const updateCartItem = (reqData) => async (dispatch) => {
    dispatch({ type: UPDATE_CART_ITEM_REQUEST })

    try {
        const { data } = await api.put(`/api/cartItem/${reqData.cartItemId}`, reqData.data)
        dispatch({ type: REMOVE_CART_ITEM_SUCCESS, payload: cartItemId })
    } catch (error) {
        dispatch({ type: REMOVE_CART_ITEM_FAILURE, payload: error.message })
    }
}

export const createOrder = (reqData) => async (dispatch) => {
    dispatch({ type: CREATE_ORDER_REQUEST })
    try {
        console.log(reqData)
        const { data } = await api.post("/api/order/add", reqData.address)
        console.log(data.id)
        if (data.id) {
            reqData.navigate({ search: `step=3&order_id=${data.id}` })
        }
        dispatch({ type: CREATE_ORDER_SUCCESS, payload: data })
    } catch (err) {
        dispatch({ type: CREATE_ORDER_FAILURE, payload: err })
    }
}

export const getOrderById = (orderId) => async (dispatch) => {
    dispatch({ type: GET_ORDER_BY_ID_REQUEST })
    try {
        const { data } = await api.get(`/api/order/get/${orderId}`)
        dispatch({ type: GET_ORDER_BY_ID_SUCCESS, payload: data })
    } catch (err) {
        dispatch({ type: GET_ORDER_BY_ID_FAILURE, payload: err.message })
    }
}

export const getOrderHistory = (reqData) => async (dispatch) => {
    dispatch({ type: GET_ORDER_HISTORY_REQUEST })
    try {
        const { data } = await api.post("/api/order/get", reqData.address)
        if (data.id) {
            reqData.navigate({ search: `step=3&order_id=${data.id}` })
        }
        dispatch({ type: GET_ORDER_HISTORY_SUCCESS, payload: data })
    } catch (err) {
        dispatch({ type: GET_ORDER_HISTORY_FAILURE, payload: err.message })
    }
}

export const createPayment = (orderId) => async (dispatch) => {
    dispatch({ type: CREATE_PAYMENT_REQUEST })
    try {
        console.log(orderId)
        const { data } = await api.post(`/api/payments/${orderId}`)

    } catch (error) {
        dispatch({ type: CREATE_PAYMENT_FAILURE, payload: error.message })
    }
}

export const updatePayment = (reqData) => async (dispatch) => {
    dispatch({ type: UPDATE_PAYMENT_REQUEST })
    try {
        const { data } = await api.get(`/api/payments?payment_id=${reqData.paymentId}&order_id=${reqData.orderId}`)
        if (data.payment_link_url) {
            window.location.href = data.payment_link_url
        }
    } catch (error) {
        dispatch({ type: UPDATE_PAYMENT_FAILURE, payload: error.message })
    }
}

export const createProduct = (product) => async (dispatch) => {
    dispatch({ type: CREATE_PRODUCT_REQUEST })
    try {
        const { data } = api.post("/api/admin/products/", product.data)
        dispatch({ type: CREATE_PRODUCT_SUCCESS, payload: data })
    } catch (error) {
        dispatch({ type: CREATE_PRODUCT_FAILURE, payload: error.message })
    }
}

export const deleteProduct = (productId) => async (dispatch) => {
    dispatch({ type: DELETE_PRODUCT_REQUEST })
    try {
        const { data } = api.post(`/api/admin/products/${productId}/delete`)
        dispatch({ type: DELETE_PRODUCT_SUCCESS, payload: data })
    } catch (error) {
        dispatch({ type: DELETE_PRODUCT_FAILURE, payload: error.message })
    }
}

export const getOrders = (reqData) => {
    console.log("get all orders ", reqData);
    return async (dispatch) => {
        dispatch(getOrdersRequest());
        try {

            const response = await api.get(`/api/admin/order/`);
            console.log("get all orders ", response.data);
            dispatch(getOrdersSuccess(response.data));
        } catch (error) {
            console.log("catch error ", error);
            dispatch(getOrdersFailure(error.message));
        }
    };
};

export const confirmOrder = (orderId) => async (dispatch) => {
    dispatch(confirmedOrderRequest());

    try {
        const response = await api.put(
            `/api/admin/orders/${orderId}/confirmed`
        );
        const data = response.data;
        console.log("confirm_order ", data)
        dispatch(confirmedOrderSuccess(data));
    } catch (error) {
        dispatch(confirmedOrderFailure(error.message));
    }
};

export const shipOrder = (orderId) => {
    return async (dispatch) => {
        try {
            dispatch(shipOrderRequest());
            const { data } = await api.put(`/api/admin/orders/${orderId}/ship`);
            console.log(" shipped order", data)
            dispatch(shipOrderSuccess(data));
        } catch (error) {
            dispatch(shipOrderFailure(error.message));
        }
    };
};

export const deliveredOrder = (orderId) => async (dispatch) => {
    dispatch(deliveredOrderRequest());

    try {
        const response = await api.put(
            `/api/admin/orders/${orderId}/deliver`
        );
        const data = response.data;
        console.log("dilivered order ", data)
        dispatch(deliveredOrderSuccess(data));
    } catch (error) {
        dispatch(deliveredOrderFailure(error.message));
    }
};

export const cancelOrder = (orderId) => async (dispatch) => {
    dispatch(canceledOrderRequest());

    try {
        const response = await api.put(
            `/api/admin/orders/${orderId}/cancel`
        );
        const data = response.data;
        dispatch(canceledOrderSuccess(data));
    } catch (error) {
        dispatch(canceledOrderFailure(error.message));
    }
};

// Async action creator for deleting an order
export const deleteOrder = (orderId) => {
    return async (dispatch) => {
        dispatch(deleteOrderRequest());
        try {
            const { data } = await api.delete(`/api/admin/orders/${orderId}/delete`);
            console.log("delete order ", data)
            dispatch(deleteOrderSuccess(orderId));
        } catch (error) {
            console.log("catch error ", error)
            dispatch(deleteOrderFailure(error));
        }

    };
};