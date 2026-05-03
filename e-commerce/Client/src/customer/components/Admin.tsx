import { InboxIcon } from "@heroicons/react/24/outline"
import { AccountCircle, Dashboard, DashboardOutlined, MailOutline } from "@mui/icons-material"
import { CssBaseline, Drawer, List, ListItem, ListItemButton, ListItemIcon, ListItemText, Toolbar, useMediaQuery, useTheme } from "@mui/material"
import { Box } from "@mui/system"
import { useState } from "react"
import { Route, Routes, useNavigate } from "react-router-dom"
import { AdminDashBoard} from "./AdminDashBoard"
import { CreateProductForm } from "./CreateProductForm"
import { ProductTable } from "./ProductTable"
import OrderTable from "./OrderTable"
import { CustomerTable } from "./CustomerTable"

export const Admin = () => {
    const menu = [
        //{ name: "Dashboard", path: "/admin", icon: <DashboardOutlined /> },
        //{ name: "Products", path: "/admin/products", icon: <DashboardOutlined /> },
        //{ name: "Customers", path: "/admin/customers", icon: <DashboardOutlined /> },
        //{ name: "Orders", path: "/admin/orders", icon: <DashboardOutlined /> },
        { name: "Add products", path: "/admin/product/create", icon: <DashboardOutlined/> },
    ]
    const theme = useTheme()
    const isLargeScreen = useMediaQuery(theme.breakpoints.up("lg"))
    const [sideBarVisible, setSideBarVisible] = useState(false)
    const navigate = useNavigate()
    const drawer = (
        <Box
            sx={{
                overflow: "auto",
                display: "flex",
                flexDirection: "column",
                justifyContent: "space-between",
                border: "1px solid blue",
                height: "100%",
            }}>
            <>
                {isLargeScreen && <Toolbar />}
                <List>
                    {menu.map((item, index) => (
                        <ListItem key={index} disablePadding onClick={() => navigate(item.path)}>
                            <ListItemButton>
                                <ListItemIcon>
                                    {item.icon}
                                </ListItemIcon>
                                <ListItemText>{item.name}</ListItemText>
                            </ListItemButton>
                        </ListItem>
                    ))}
                </List>
            </>
            <List>
                <ListItem disablePadding>
                    <ListItemButton>
                        <ListItemIcon>
                            <AccountCircle/>
                        </ListItemIcon>
                        <ListItemText onClick={() => navigate("/")}>Account</ListItemText>
                    </ListItemButton>
                </ListItem>
            </List>
        </Box>
    )

    return (<>
        <div>
            <CssBaseline />
            <div className="border border-r-gray-300 h-full"></div>
            <Drawer
                variant="permanent" sx={{
                    height: "100vh", border: "1px solid black",
                    
                }}>
                {drawer}
            </Drawer>
            <div className="w-[85%]" style={{paddingLeft:250}}>
                <Routes>
                    <Route path="/" element={<AdminDashBoard />} />
                    <Route path="/product/create" element={<CreateProductForm />} />
                    <Route path="/products" element={<ProductTable />} />
                    <Route path="/orders" element={<OrderTable />} />
                    <Route path="/customers" element={<CustomerTable />} />
                </Routes>
            </div>
        </div>
    </>)
}
