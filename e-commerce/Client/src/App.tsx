import { Route, Routes } from "react-router-dom"
import { CustomerRoutes } from "./customer/components/CustomerRoutes"
import { AdminRoutes } from "./customer/components/AdminRoutes"
function App() {
  return (<>
    <Routes>
      <Route path="/*" element={<CustomerRoutes />} />
      <Route path="/admin/*" element={<AdminRoutes />} />
    </Routes>
  </>)
}
export default App