export const navigation = {
    categories: [
        {
            id: 'women',
            name: 'Women',
            featured: [
                {
                    name: 'New Arrivals',
                    href: '/',
                    imageSrc: 'https://tailwindui.com/img/ecommerce-images/mega-menu-category-01.jpg',
                    imageAlt: 'Models sitting back to back, wearing Basic Tee in black and bone.',
                },
                {
                    name: 'Basic Tees',
                    href: '/',
                    imageSrc: 'https://tailwindui.com/img/ecommerce-images/mega-menu-category-02.jpg',
                    imageAlt: 'Close up of Basic Tee fall bundle with off-white, ochre, olive, and black tees.',
                },
            ],
            sections: [
                {
                    id: 'clothing',
                    name: 'Clothing',
                    items: [
                        { name: 'Tops', id: "top", href: `{women/clothing/tops}` },
                        { name: 'Dresses', id: "women_dress", href: '#' },
                        { name: 'Women Jeans', id: 'women_jeans' },
                        { name: 'Lengha Choli', id: 'lengha_choli' },
                        { name: 'Sweaters', id: 'womens_sweater' },
                        { name: 'T-Shirts', id: 'womens_t-shirt' },
                        { name: 'Jackets', id: 'womens_jacket' },
                        { name: 'Gouns', id: 'womens_gouns' },
                        { name: 'Sarees', id: 'womens_saree' },
                        { name: 'Kurtas', id: 'womens_kurtas' },
                    ],
                },
                {
                    id: 'accessories',
                    name: 'Accessories',
                    items: [
                        { name: 'Watches', id: 'womens_watch' },
                        { name: 'Wallets', id: 'womens_wallet' },
                        { name: 'Bags', id: 'womens_bag' },
                        { name: 'Sunglasses', id: 'womens_sunglass' },
                        { name: 'Hats', id: 'womens_hat' },
                        { name: 'Belts', id: 'womens_belt' },
                    ],
                },
                {
                    id: 'brands',
                    name: 'Brands',
                    items: [
                        { name: 'Full Nelson', id: '#' },
                        { name: 'My Way', id: '#' },
                        { name: 'Re-Arranged', id: '#' },
                        { name: 'Counterfeit', id: '#' },
                        { name: 'Significant Other', id: '#' },
                    ],
                },
            ],
        },
        {
            id: 'mens',
            name: 'Men',
            featured: [
                {
                    name: 'New Arrivals',
                    id: '#',
                    imageSrc: 'https://tailwindui.com/img/ecommerce-images/product-page-04-detail-product-shot-01.jpg',
                    imageAlt: 'Drawstring top with elastic loop closure and textured interior padding.',
                },
                {
                    name: 'Artwork Tees',
                    id: '#',
                    imageSrc: 'https://tailwindui.com/img/ecommerce-images/category-page-02-image-card-06.jpg',
                    imageAlt:
                        'Three shirts in gray, white, and blue arranged on table with same line drawing of hands and shapes overlapping on front of shirt.',
                },
            ],
            sections: [
                {
                    id: 'clothing',
                    name: 'Clothing',
                    items: [
                        { name: 'Kurtas', id: 'mens_kurta' },
                        { name: 'Shirt', id: 'mens_shirt' },
                        { name: 'Jeans', id: 'mens_jeans' },
                        { name: 'Sweaters', id: 'mens_sweater' },
                        { name: 'T-Shirts', id: 'mens_t-shirt' },
                        { name: 'Jackets', id: 'mens_jacket' },
                        { name: 'Activewear', id: 'mens_activewear' },

                    ],
                },
                {
                    id: 'accessories',
                    name: 'Accessories',
                    items: [
                        { name: 'Watches', id: 'mens_watch' },
                        { name: 'Wallets', id: 'mens_wallet' },
                        { name: 'Bags', id: 'mens_bags' },
                        { name: 'Sunglasses', id: 'mens_sunglass' },
                        { name: 'Hats', id: 'mens_hat' },
                        { name: 'Belts', id: 'mens_belt' },
                    ],
                },
                {
                    id: 'brands',
                    name: 'Brands',
                    items: [
                        { name: 'Re-Arranged', id: '#' },
                        { name: 'Counterfeit', id: '#' },
                        { name: 'Full Nelson', id: '#' },
                        { name: 'My Way', id: '#' },
                    ],
                },
            ],
        },
    ],
    pages: [
        { name: '', id: '/' },
        { name: '', id: '/' },
    ],
}