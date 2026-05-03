import { Route, Routes } from "react-router-dom"
import { HomePage } from "./HomePage"
import { Cart } from "./Cart"
import Navigation from "./Navigation"
import { Footer } from "./Footer"
import Product from "./Product"
import ProductDetails from "./ProductDetails"
import CheckOut from "./CheckOut"
import { Order } from "./Order"
import { OrderDetails } from "./OrderDetails"
import { PaymentSuccessful } from "./PaymentSuccessful"

export const CustomerRoutes = () => {
  return (<>
    <Navigation />
    <Routes>
      <Route path="/login" element={<HomePage />} />
      <Route path="/register" element={<HomePage />} />
      <Route path="/" element={<HomePage />} />
      <Route path="/cart" element={<Cart />} />
      <Route path="/:levelOne/:levelTwo/:levelThree" element={<Product />} />
      <Route path="/product/:productId" element={<ProductDetails />} />
      <Route path="/checkout" element={<CheckOut />} />
      <Route path="/account/order" element={<Order />} />
      <Route path="/account/order/:orderId" element={<OrderDetails />} />
      <Route path="/payment/:orderId" element={<PaymentSuccessful />} />
    </Routes>
    <div>
      <Footer />
    </div>
  </>)
}