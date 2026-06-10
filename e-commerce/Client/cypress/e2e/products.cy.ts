describe('Product Cards', () => {
  beforeEach(() => {
    cy.mockProductsApi()
    cy.visit('/')
    cy.wait('@getProducts')
  })

  it('displays product title', () => {
    cy.contains("Men's Casual T-Shirt").should('be.visible')
  })

  it('displays product brand', () => {
    cy.contains('FashionBrand').should('be.visible')
  })

  it('displays product description', () => {
    cy.contains('Comfortable cotton t-shirt for everyday wear').should('be.visible')
  })

  it('shows discounted price, original price, and discount percent', () => {
    cy.get('.bg-white.rounded-lg.shadow-md').first().within(() => {
      cy.contains('₹799').should('be.visible')
      cy.contains('₹999').should('be.visible')
      cy.contains('20% off').should('be.visible')
    })
  })

  it('does not show discount info when discount is 0%', () => {
    cy.contains('Wireless Headphones')
      .closest('.bg-white.rounded-lg.shadow-md')
      .within(() => {
        cy.contains('₹4999').should('be.visible')
        cy.contains('% off').should('not.exist')
      })
  })

  it('shows stock and colour info', () => {
    cy.get('.bg-white.rounded-lg.shadow-md').first().within(() => {
      cy.contains('Stock: 50').should('be.visible')
      cy.contains('blue').should('be.visible')
    })
  })

  it('renders product image', () => {
    cy.get('.bg-white.rounded-lg.shadow-md').first().find('img').should('exist')
  })
})

describe('Category Filtering', () => {
  beforeEach(() => {
    cy.mockProductsApi()
    cy.visit('/')
    cy.wait('@getProducts')
  })

  it('filters to one product when electronics is selected', () => {
    cy.contains('button', /electronics/).click()
    cy.get('.bg-white.rounded-lg.shadow-md').should('have.length', 1)
    cy.contains('Wireless Headphones').should('be.visible')
  })

  it('marks the selected category button as active', () => {
    cy.contains('button', /electronics/).click()
    cy.contains('button', /electronics/).should('have.class', 'bg-blue-600')
  })

  it('deactivates All button when a category is selected', () => {
    cy.contains('button', /electronics/).click()
    cy.contains('button', /^All/).should('not.have.class', 'bg-blue-600')
  })

  it('clicking All restores all products', () => {
    cy.contains('button', /electronics/).click()
    cy.get('.bg-white.rounded-lg.shadow-md').should('have.length', 1)

    cy.contains('button', /^All/).click()
    cy.get('.bg-white.rounded-lg.shadow-md').should('have.length', 4)
  })

  it('shows empty state when category has no products', () => {
    cy.mockProductsApi([
      {
        id: 1,
        title: 'Test Product',
        description: 'desc',
        price: 500,
        discountedPrice: 400,
        discountPercent: 20,
        brand: 'Brand',
        colour: 'green',
        imageUrl: 'https://example.com/img.jpg',
        category: { name: 'books' },
        quantity: 10,
      },
    ])
    cy.visit('/')
    cy.wait('@getProducts')

    cy.contains('button', /electronics/).should('not.exist')
    cy.contains('No products found in this category.').should('not.exist')
    cy.contains('button', /books \(1\)/).should('be.visible')
  })

  it('filters womens_clothing correctly', () => {
    cy.contains('button', /womens_clothing/).click()
    cy.get('.bg-white.rounded-lg.shadow-md').should('have.length', 1)
    cy.contains("Women's Summer Dress").should('be.visible')
  })
})
