import { useEffect } from "react"
import { useDispatch, useSelector } from "react-redux"
import { HomeSectionCarousel } from "./HomeSectionCarousel"
import MainCarousel from "./MainCarousel"
import { getAllProducts } from "./Action"

export const HomePage = () => {
    const dispatch = useDispatch()
    const { products, loading } = useSelector(state => state.product)

    useEffect(() => {
        // Fetch all products when component mounts
        dispatch(getAllProducts())
    }, [dispatch])

    // Group products by category for display
    const getProductsByCategory = (categoryPrefix) => {
        return products.filter(p =>
            p.category && p.category.name && p.category.name.startsWith(categoryPrefix)
        )
    }

    // New category sections matching backend data
    const electronics = getProductsByCategory("electronics")
    const mensClothing = getProductsByCategory("mens_")
    const womensClothing = getProductsByCategory("womens_")
    const homeLiving = getProductsByCategory("home")
    const books = getProductsByCategory("books")

    // console.log("[HOMEPAGE] Products loaded:", products.length)
    // console.log("[HOMEPAGE] Loading:", loading)

    return (<><div>
        <MainCarousel />
    </div>
        <div className="space-y-10 py-20 flex flex-col justify-center px-5 lg:px-10">
            {electronics.length > 0 && <HomeSectionCarousel data={electronics.slice(0, 10)} sectionName={"Featured Electronics"} />}
            {mensClothing.length > 0 && <HomeSectionCarousel data={mensClothing.slice(0, 10)} sectionName={"Men's Fashion"} />}
            {womensClothing.length > 0 && <HomeSectionCarousel data={womensClothing.slice(0, 10)} sectionName={"Women's Fashion"} />}
            {homeLiving.length > 0 && <HomeSectionCarousel data={homeLiving.slice(0, 8)} sectionName={"Home & Living"} />}
            {books.length > 0 && <HomeSectionCarousel data={books.slice(0, 8)} sectionName={"Best Books"} />}

            {/* Fallback: Show all products if no category filtering yields results */}
            {products.length > 0 && mensClothing.length === 0 && womensClothing.length === 0 && (
                <HomeSectionCarousel data={products} sectionName={"All Products"} />
            )}
        </div>
    </>
    )
}
