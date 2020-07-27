import {
  AccountInfo,
  InteractionRequiredAuthError,
  PublicClientApplication,
  RedirectRequest,
  SilentRequest
} from '@azure/msal-browser';
import { config, graphTaskScopes, redirectUri, yammerScopes } from './config';

export class Authenticator {
  private app: PublicClientApplication;
  private account: AccountInfo | null;

  constructor() {
    this.app = new PublicClientApplication(config);
    this.account = null;
  }

  public async handleRedirect() {
    try {
      const response = await this.app.handleRedirectPromise();

      if (response) {
        this.account = response.account;
      } else {
        const account = this.getAccount();

        if (account) {
          this.account = account;
        }
      }
    } catch (e) {
      console.error(e);
    }
  }

  public hasAccount() {
    const accounts = this.app.getAllAccounts();

    return accounts != null && accounts.length > 0;
  }

  public login() {
    const loginRedirectRequest = {
      scopes: [],
      redirectStartPage: window.location.href
    };

    this.app.loginRedirect(loginRedirectRequest);
  }

  public acquireGraphToken() {
    return this.acquireToken(graphTaskScopes);
  }

  public acquireYammerToken() {
    return this.acquireToken(yammerScopes);
  }

  private getAccount() {
    const accounts = this.app.getAllAccounts();

    if (accounts == null || accounts.length === 0) {
      return null;
    }

    if (accounts.length > 1) {
      // TODO: Show account picker when multiple accounts exist
      alert('multiple accounts found');
    }

    return accounts[0];
  }

  private async acquireToken(scopes: string[]): Promise<string> {
    if (!this.account) {
      throw new Error('No account. User is not logged in.');
    }

    const silentRequest: SilentRequest = {
      scopes,
      account: this.account,
      redirectUri,
    };

    try {
      const { accessToken } = await this.app.acquireTokenSilent(silentRequest);

      return accessToken;
    } catch (e) {
      console.log('acquireTokenSilent failed', e);

      if (e instanceof InteractionRequiredAuthError) {
        const redirectRequest: RedirectRequest = {
          ...silentRequest,
          redirectStartPage: window.location.href,
        };

        await this.app.acquireTokenRedirect(redirectRequest);
        throw new Error('Acquiring token through redirect flow');
      }

      throw e;
    }
  }
}
