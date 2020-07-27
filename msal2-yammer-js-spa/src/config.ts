import { Configuration } from '@azure/msal-browser';

export const config: Configuration = {
  auth: {
    clientId: 'Your AAD app id goes here',
    authority: 'https://login.microsoftonline.com/organizations',
  },
  cache: {
    cacheLocation: 'localStorage',
    storeAuthStateInCookie: false,
  },
};

export const redirectUri = 'http://localhost:8080';

export const graphTaskScopes = [
  'Tasks.Read',
  'Tasks.Read.Shared',
  'Tasks.ReadWrite',
  'Tasks.ReadWrite.Shared',
  'User.Read',
];

export const yammerScopes = [
  'https://www.yammer.com/user_impersonation'
];
