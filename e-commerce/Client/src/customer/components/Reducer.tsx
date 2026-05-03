import { ADD_ITEM_TO_CART_FAILURE, ADD_ITEM_TO_CART_REQUEST, ADD_ITEM_TO_CART_SUCCESS, CANCELED_ORDER_FAILURE, CANCELED_ORDER_REQUEST, CANCELED_ORDER_SUCCESS, CHANGE_NUMBER_FAILURE, CHANGE_NUMBER_REQUEST, CHANGE_NUMBER_SUCCESS, CONFIRMED_ORDER_FAILURE, CONFIRMED_ORDER_REQUEST, CONFIRMED_ORDER_SUCCESS, CREATE_ORDER_FAILURE, CREATE_ORDER_REQUEST, CREATE_ORDER_SUCCESS, DELETE_ORDER_FAILURE, DELETE_ORDER_REQUEST, DELETE_ORDER_SUCCESS, DELETE_PRODUCT_SUCCESS, DELIVERED_ORDER_FAILURE, DELIVERED_ORDER_REQUEST, DELIVERED_ORDER_SUCCESS, FIND_PRODUCTS_FAILURE, FIND_PRODUCTS_REQUEST, FIND_PRODUCTS_SUCCESS, FIND_PRODUCT_BY_ID_FAILURE, FIND_PRODUCT_BY_ID_REQUEST, FIND_PRODUCT_BY_ID_SUCCESS, GET_ALL_CUSTOMERS_SUCCESS, GET_ALL_PRODUCTS_FAILURE, GET_ALL_PRODUCTS_REQUEST, GET_ALL_PRODUCTS_SUCCESS, GET_CART_FAILURE, GET_CART_REQUEST, GET_CART_SUCCESS, GET_ORDERS_FAILURE, GET_ORDERS_REQUEST, GET_ORDERS_SUCCESS, GET_ORDER_BY_ID_FAILURE, GET_ORDER_BY_ID_REQUEST, GET_ORDER_BY_ID_SUCCESS, GET_ORDER_HISTORY_FAILURE, GET_ORDER_HISTORY_REQUEST, GET_ORDER_HISTORY_SUCCESS, GET_USER_FAILURE, GET_USER_REQUEST, GET_USER_SUCCESS, LOGIN_FAILURE, LOGIN_REQUEST, LOGIN_SUCCESS, LOGOUT, PLACED_ORDER_FAILURE, PLACED_ORDER_REQUEST, PLACED_ORDER_SUCCESS, REGISTER_FAILURE, REGISTER_REQUEST, REGISTER_SUCCESS, REMOVE_CART_ITEM_FAILURE, REMOVE_CART_ITEM_REQUEST, REMOVE_CART_ITEM_SUCCESS, SHIP_ORDER_FAILURE, SHIP_ORDER_REQUEST, SHIP_ORDER_SUCCESS, UPDATE_CART_ITEM_FAILURE, UPDATE_CART_ITEM_REQUEST, UPDATE_CART_ITEM_SUCCESS } from "./ActionType"

const initialState = {
  user: null,
  isLoading: false,
  error: null,
  jwt: null
};
export const authReducer = (state = initialState, action) => {
  switch (action.type) {
    case REGISTER_REQUEST:
    case LOGIN_REQUEST:
      return { ...state, isLoading: true, error: null };

    case REGISTER_SUCCESS:
      return { ...state, isLoading: false };
    case REGISTER_FAILURE:
    case LOGIN_FAILURE:
      return { ...state, isLoading: false, error: action.payload };
    case LOGIN_SUCCESS:
      return { ...state, isLoading: false };
    case GET_USER_REQUEST:
      return { ...state, isLoading: true, error: null, fetchingUser: true };
    case GET_USER_SUCCESS:
      return {
        ...state,
        isLoading: false,
        user: action.payload,
        fetchingUser: false,
      };
    case GET_ALL_CUSTOMERS_SUCCESS:
      return {
        ...state,
        isLoading: false,
        customers: action.payload,
      };
    case GET_USER_FAILURE:
      return {
        ...state,
        isLoading: false,
        error: action.payload,
        fetchingUser: false,
      };
    case LOGOUT:
      localStorage.removeItem("jwt");
      return { ...state, jwt: null, user: null };
    default:
      return state
  }
}

const productInitializeState = {
  products: [],
  product: null,
  loading: false,
  error: null
}

export const customerProductReducer = (state = productInitializeState, action) => {

  switch (action.type) {
    case GET_ALL_PRODUCTS_REQUEST:
      return { ...state, loading: true, error: null,products:[]};
    case GET_ALL_PRODUCTS_SUCCESS:
      return { ...state, loading: false, error: null, products: action.payload};
    case GET_ALL_PRODUCTS_FAILURE:
      return { ...state, loading: false,products:[], error: action.payload };
    case FIND_PRODUCTS_REQUEST:
    case FIND_PRODUCT_BY_ID_REQUEST:
      return { ...state, loading: true, error: null,product:null}
    case FIND_PRODUCTS_SUCCESS:
      return { ...state, loading: false, error: null}
    case FIND_PRODUCT_BY_ID_SUCCESS:
      return { ...state, loading: false, error: null, product: action.payload}
    case DELETE_PRODUCT_SUCCESS:
      return { ...state, loading: false, error: null, deletedProduct: action.payload }
    case FIND_PRODUCTS_FAILURE:
    case FIND_PRODUCT_BY_ID_FAILURE:
      return { ...state, loading: false, error: action.payload }
    default:
      return state
  }
}

const cartInitialState = {
  cart: null,
  loading: false,
  error: null,
  cartItems: []
}

export const cartReducer = (state = cartInitialState, action) => {
  switch (action.type) {
    case ADD_ITEM_TO_CART_REQUEST:
      return { ...state, loading: true, error: null }
    case ADD_ITEM_TO_CART_SUCCESS:
      return {
        ...state,
        cartItems: [...state.cartItems, action.payload.cartItems],
        loading: false
      }
    case ADD_ITEM_TO_CART_FAILURE:
      return { ...state, loading: false, error: action.payload }
    case GET_CART_REQUEST:
      return { ...state, loading: true }
    case GET_CART_SUCCESS:
      return {
        ...state,
        cartItems: action.payload.cartItems,
        cart: action.payload,
        loading: false,
      }
    case GET_CART_FAILURE:
      return { ...state, loading: false, error: action.payload }
    case REMOVE_CART_ITEM_REQUEST:
    case UPDATE_CART_ITEM_REQUEST:
      return { ...state, loading: true, error: null }
    case REMOVE_CART_ITEM_SUCCESS:
      return {
        ...state,
        deleteCartItem: action.payload,
        loading: false
      }
    case UPDATE_CART_ITEM_SUCCESS:
      return {
        ...state,
        updateCartItem: action.payload,
        loading: false
      }
    case REMOVE_CART_ITEM_FAILURE:
    case UPDATE_CART_ITEM_FAILURE:
      return { ...state, loading: false, error: action.payload }
    case CHANGE_NUMBER_REQUEST:
      return {
        ...state,
        loading: true,
        error: null
      };
    case CHANGE_NUMBER_SUCCESS:
      return {
        ...state,
        cartItems: state.cartItems.map(item =>
          item.id === action.payload.cartItemId ? { ...item, quantity: action.payload.quantity } : item
        ),
        loading: false
      };
    case CHANGE_NUMBER_FAILURE:
      return {
        ...state,
        loading: false,
        error: action.payload
      };
    default:
      return state
  }

}

const orderInitialState = {
  loading: false,
  error: null,
  order: null,
  orders: []
}

export const orderReducer = (state = orderInitialState, action) => {
  switch (action.type) {
    case CREATE_ORDER_REQUEST:
      return {
        ...state,
        loading: true
      }
    case CREATE_ORDER_SUCCESS:
      return {
        ...state,
        loading: false,
        success: true,
        order: action.payload
      }
    case CREATE_ORDER_FAILURE:
      return {
        ...state,
        loading: false,
        error: action.payload
      }
    case GET_ORDER_BY_ID_REQUEST:
      return {
        ...state,
        loading: true,
        error: null
      }
    case GET_ORDER_BY_ID_SUCCESS:
      return {
        ...state,
        loading: false,
        order: action.payload
      }
    case GET_ORDER_BY_ID_FAILURE:
      return {
        ...state,
        loading: false,
        error: action.payload
      }
    case GET_ORDER_HISTORY_REQUEST:
      return {
        loading: true,
        error: null,
        orders: []
      }
    case GET_ORDER_HISTORY_SUCCESS:
      return {
        loading: false,
        orders: action.payload
      }
    case GET_ORDER_HISTORY_FAILURE:
      return {
        loading: false,
        error: action.payload
      }
    default:
      return state
  }
}

const adminOrderInitialState = {
  loading: false,
  orders: [],
  error: "",
};

export const adminOrderReducer = (state = adminOrderInitialState, action) => {
  switch (action.type) {
    case GET_ORDERS_REQUEST:
      return {
        ...state,
        loading: true,
      };
    case GET_ORDERS_SUCCESS:
      return {
        loading: false,
        orders: action.payload,
        error: "",
      };
    case GET_ORDERS_FAILURE:
      return {
        loading: false,
        orders: [],
        error: action.payload,
      };
    case CONFIRMED_ORDER_REQUEST:
    case PLACED_ORDER_REQUEST:
    case DELIVERED_ORDER_REQUEST:
    case CANCELED_ORDER_REQUEST:
      return {
        ...state,
        isLoading: true,
      };
    case CONFIRMED_ORDER_SUCCESS:
      return {
        ...state,
        confirmed: action.payload,
        isLoading: false,
      };
    case PLACED_ORDER_SUCCESS:
      return {
        ...state,
        placed: action.payload,
        isLoading: false,
      };
    case DELIVERED_ORDER_SUCCESS:
      return {
        ...state,
        delivered: action.payload,
        isLoading: false,
      };
    case CANCELED_ORDER_SUCCESS:
      return {
        ...state,
        canceled: action.payload,
        isLoading: false,
      };

    case CONFIRMED_ORDER_FAILURE:
    case PLACED_ORDER_FAILURE:
    case DELIVERED_ORDER_FAILURE:
    case CANCELED_ORDER_FAILURE:
      return {
        ...state,
        error: action.payload,
        isLoading: false,
      };

    case DELETE_ORDER_REQUEST:
      return { ...state, loading: true };
    case DELETE_ORDER_SUCCESS:
      return { ...state, loading: false, orders: state.orders.filter((order) => order.id !== action.payload) };
    case DELETE_ORDER_FAILURE:
      return { ...state, loading: false, error: action.payload };

    case SHIP_ORDER_REQUEST:
      return {
        ...state,
        isLoading: true,
        error: null,
      };
    case SHIP_ORDER_SUCCESS:
      return {
        ...state,
        isLoading: false,
        shipped: action.payload
      };
    case SHIP_ORDER_FAILURE:
      return {
        ...state,
        isLoading: false,
        error: action.payload,
      };
    default:
      return state;
  }
};
