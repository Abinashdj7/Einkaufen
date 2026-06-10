describe('Home Page', () => {
  beforeEach(() => {
    cy.mockProductsApi()
  })

  it('shows page title', () => {
    cy.visit('/')
    cy.wait('@getProducts')
    cy.contains('h1', 'E-Commerce Store').should('be.visible')
  })

  it('shows product count in header', () => {
    cy.visit('/')
    cy.wait('@getProducts')
    cy.contains('Total products: 4').should('be.visible')
  })

  it('shows loading spinner before products load', () => {
    cy.intercept('GET', '**/api/allProducts', (req) => {
      req.reply((res) => {
        res.setDelay(400)
        res.send({ statusCode: 200, body: [] })
      })
    }).as('slowProducts')

    cy.visit('/')
    cy.contains('Loading products...').should('be.visible')
  })

  it('renders all product cards after load', () => {
    cy.visit('/')
    cy.wait('@getProducts')
    cy.get('.bg-white.rounded-lg.shadow-md').should('have.length', 4)
  })

  it('shows All category button with correct count', () => {
    cy.visit('/')
    cy.wait('@getProducts')
    cy.contains('button', 'All (4)').should('be.visible').and('have.class', 'bg-blue-600')
  })

  it('shows one button per unique category', () => {
    cy.visit('/')
    cy.wait('@getProducts')
    cy.contains('button', 'mens_clothing (1)').should('be.visible')
    cy.contains('button', 'womens_clothing (1)').should('be.visible')
    cy.contains('button', 'electronics (1)').should('be.visible')
    cy.contains('button', 'footwear (1)').should('be.visible')
  })
})

describe('Home Page - Error State', () => {
  it('shows error message when API fails', () => {
    cy.mockProductsApiError()
    cy.visit('/')
    cy.wait('@getProductsError')
    cy.contains('Failed to load products. Please check the backend is running.').should('be.visible')
  })

  it('shows Try Again button on error', () => {
    cy.mockProductsApiError()
    cy.visit('/')
    cy.wait('@getProductsError')
    cy.contains('button', 'Try Again').should('be.visible')
  })

  it('retries and loads products when Try Again is clicked', () => {
    let callCount = 0
    cy.intercept('GET', '**/api/allProducts', (req) => {
      callCount++
      if (callCount === 1) {
        req.reply({ statusCode: 500, body: 'Server Error' })
      } else {
        req.reply({
          statusCode: 200,
          body: [
            {
              id: 1,
              title: 'Retry Product',
              description: 'Loaded after retry',
              price: 500,
              discountedPrice: 400,
              discountPercent: 20,
              brand: 'Brand',
              colour: 'blue',
              imageUrl: '',
              category: { name: 'test' },
              quantity: 10,
            },
          ],
        })
      }
    }).as('productsWithRetry')

    cy.visit('/')
    cy.wait('@productsWithRetry')
    cy.contains('button', 'Try Again').click()
    cy.wait('@productsWithRetry')
    cy.contains('Total products: 1').should('be.visible')
  })
})

describe('Home Page - Empty State', () => {
  it('shows empty state message when API returns no products', () => {
    cy.mockProductsApi([])
    cy.visit('/')
    cy.wait('@getProducts')
    cy.contains('No products found in this category.').should('be.visible')
  })
})
