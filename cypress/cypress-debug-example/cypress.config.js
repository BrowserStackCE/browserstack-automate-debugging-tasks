module.exports = {
  'projectId': '4b7344',
  e2e: {
    setupNodeEvents(on, config) {
      on('task', {
        async 'db:seed'() {
          // seed database with test data
          const axios = require('axios');
          return null;
        },
      })
    },
  },
}
