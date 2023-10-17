/// <reference types="cypress" />
//var _ = require('lodash');

describe('example to-do app', () => {
  beforeEach(() => {
    cy.visit('www.google.com')
  })

  it('displays two todo items by default', () => {
    //_.chunk(['a', 'b', 'c', 'd'], 2);
    cy.task("db:seed");
  })
})
