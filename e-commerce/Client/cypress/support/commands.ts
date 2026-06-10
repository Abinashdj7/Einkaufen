declare global {
  namespace Cypress {
    interface Chainable {
      mockProductsApi(products?: object[]): Chainable<void>
      mockProductsApiError(): Chainable<void>
    }
  }
}

Cypress.Commands.add('mockProductsApi', (products) => {
  if (products) {
    cy.intercept('GET', '**/api/allProducts', { statusCode: 200, body: products }).as('getProducts')
  } else {
    cy.fixture('products').then((defaultProducts) => {
      cy.intercept('GET', '**/api/allProducts', { statusCode: 200, body: defaultProducts }).as('getProducts')
    })
  }
})

Cypress.Commands.add('mockProductsApiError', () => {
  cy.intercept('GET', '**/api/allProducts', {
    statusCode: 500,
    body: { message: 'Internal Server Error' },
  }).as('getProductsError')
})

export {}
