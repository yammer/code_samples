import { Authenticator } from './authenticator';
import { callGraphApi } from './api/graph';
import { callYammerApi } from './api/yammer';

export const renderSignIn = (authenticator: Authenticator) => {
  const signInButton = document.createElement('button');

  signInButton.innerText = 'Sign In';

  signInButton.addEventListener('click', () => {
    authenticator.login();
  });

  document.body.appendChild(signInButton);
};

export const renderApp = (authenticator: Authenticator) => {
  const acquireGraphToken: AcquireToken = () => authenticator.acquireGraphToken();
  const acquireYammerToken: AcquireToken = () => authenticator.acquireYammerToken();

  renderApiButton(acquireYammerToken, callYammerApi, 'api/v1/messages.json?limit=1&threaded=true');
  renderApiButton(acquireYammerToken, callYammerApi, 'api/v1/users/current.json');
  renderApiButton(acquireGraphToken, callGraphApi, 'me/todo/lists/Tasks/tasks');
};

type AcquireToken = () => Promise<string>;
type CallApi = (accessToken: string, endpoint: string) => Promise<any>;

const renderApiButton = (acquireToken: AcquireToken, callApi: CallApi, endpoint: string) => {
  const result = document.createElement('code');
  const fetchButton = document.createElement('button');

  fetchButton.style.display = 'block';
  fetchButton.style.marginTop = '30px';
  fetchButton.innerText = `Fetch ${endpoint}`;

  fetchButton.addEventListener('click', async () => {
    fetchButton.setAttribute('disabled', 'disabled');
    result.innerText = 'Fetching...';

    try {
      const accessToken = await acquireToken();
      const data = await callApi(accessToken, endpoint);

      result.innerText = JSON.stringify(data);
    } catch (e) {
      result.innerText = JSON.stringify(e);
    }

    fetchButton.removeAttribute('disabled');
  });

  document.body.appendChild(fetchButton);
  document.body.appendChild(result);
};
