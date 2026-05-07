import { useState, useEffect } from 'react'
import axios from 'axios'

interface Product {
  id: number
  title: string
  description: string
  price: number
  discountedPrice: number
  discountPercent: number
  brand: string
  colour: string
  imageUrl: string
  category: {
    name: string
  }
  quantity: number
}

function App() {
  const [products, setProducts] = useState<Product[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)
  const [selectedCategory, setSelectedCategory] = useState<string>('all')

  const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || (
    typeof window !== 'undefined' && window.location.hostname === 'localhost'
      ? 'http://localhost:8080'
      : '/api'
  )

  useEffect(() => {
    fetchProducts()
  }, [])

  const fetchProducts = async () => {
    try {
      setLoading(true)
      setError(null)

      const apiUrl = API_BASE_URL === '/api'
        ? '/api/allProducts'
        : `${API_BASE_URL}/api/allProducts`

      console.log('[FRONTEND] Fetching products from:', apiUrl)

      const response = await axios.get(apiUrl)
      console.log('[FRONTEND] API Response:', response.data)

      setProducts(response.data)
    } catch (err) {
      console.error('[FRONTEND] Error fetching products:', err)
      setError('Failed to load products. Please check the backend is running.')
    } finally {
      setLoading(false)
    }
  }

  const getCategories = () => {
    const categories = new Set(products.map(p => p.category?.name).filter(Boolean))
    return Array.from(categories)
  }

  const filteredProducts = selectedCategory === 'all'
    ? products
    : products.filter(p => p.category?.name === selectedCategory)

  if (loading) {
    return (
      <div className="min-h-screen bg-gray-100 flex items-center justify-center">
        <div className="text-center">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600 mx-auto mb-4"></div>
          <p className="text-gray-600">Loading products...</p>
        </div>
      </div>
    )
  }

  if (error) {
    return (
      <div className="min-h-screen bg-gray-100 flex items-center justify-center">
        <div className="text-center">
          <div className="text-red-500 text-xl mb-4">⚠️ Error</div>
          <p className="text-gray-600 mb-4">{error}</p>
          <button
            onClick={fetchProducts}
            className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
          >
            Try Again
          </button>
        </div>
      </div>
    )
  }

  return (
    <div className="min-h-screen bg-gray-100">
      <header className="bg-white shadow-sm">
        <div className="max-w-7xl mx-auto px-4 py-6">
          <h1 className="text-3xl font-bold text-gray-900">E-Commerce Store</h1>
          <p className="text-gray-600 mt-2">Total products: {products.length}</p>
        </div>
      </header>

      <div className="max-w-7xl mx-auto px-4 py-8">
        <div className="mb-8">
          <h2 className="text-xl font-semibold mb-4">Categories</h2>
          <div className="flex flex-wrap gap-2">
            <button
              onClick={() => setSelectedCategory('all')}
              className={`px-4 py-2 rounded ${selectedCategory === 'all'
                ? 'bg-blue-600 text-white'
                : 'bg-white text-gray-700 border border-gray-300 hover:bg-gray-50'
                }`}
            >
              All ({products.length})
            </button>
            {getCategories().map(category => (
              <button
                key={category}
                onClick={() => setSelectedCategory(category)}
                className={`px-4 py-2 rounded ${selectedCategory === category
                  ? 'bg-blue-600 text-white'
                  : 'bg-white text-gray-700 border border-gray-300 hover:bg-gray-50'
                  }`}
              >
                {category} ({products.filter(p => p.category?.name === category).length})
              </button>
            ))}
          </div>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
          {filteredProducts.map(product => (
            <div key={product.id} className="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow">
              <div className="aspect-square overflow-hidden">
                <img
                  src={product.imageUrl}
                  alt={product.title}
                  className="w-full h-full object-cover hover:scale-105 transition-transform"
                  onError={(e) => {
                    const target = e.target as HTMLImageElement
                    target.src = 'https://via.placeholder.com/300x300?text=No+Image'
                  }}
                />
              </div>
              <div className="p-4">
                <h3 className="font-semibold text-lg text-gray-900 mb-2 line-clamp-2">
                  {product.title}
                </h3>
                <p className="text-gray-600 text-sm mb-2">{product.brand}</p>
                <p className="text-gray-500 text-sm mb-3 line-clamp-2">
                  {product.description}
                </p>
                <div className="flex items-center justify-between">
                  <div className="flex items-center space-x-2">
                    <span className="text-lg font-bold text-green-600">
                      ₹{product.discountedPrice}
                    </span>
                    {product.discountPercent > 0 && (
                      <>
                        <span className="text-sm text-gray-500 line-through">
                          ₹{product.price}
                        </span>
                        <span className="text-sm text-red-500 font-medium">
                          {product.discountPercent}% off
                        </span>
                      </>
                    )}
                  </div>
                </div>
                <div className="mt-3 flex items-center justify-between text-sm text-gray-500">
                  <span>Stock: {product.quantity}</span>
                  <span className="capitalize">{product.colour}</span>
                </div>
              </div>
            </div>
          ))}
        </div>

        {filteredProducts.length === 0 && (
          <div className="text-center py-12">
            <p className="text-gray-500 text-lg">No products found in this category.</p>
          </div>
        )}
      </div>
    </div>
  )
}

export default App