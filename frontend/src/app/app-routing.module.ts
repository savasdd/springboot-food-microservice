import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {DefaultLayoutComponent} from './containers';
import {Page404Component} from './views/pages/page404/page404.component';
import {Page500Component} from './views/pages/page500/page500.component';
import {LoginComponent} from './views/pages/login/login.component';
import {RegisterComponent} from './views/pages/register/register.component';

const routes: Routes = [
  {
    path: '', redirectTo: 'dashboard', pathMatch: 'full'
  },
  {
    path: '', component: DefaultLayoutComponent,
    data: {
      title: 'Home'
    },
    children: [
      {
        path: 'dashboard',
        loadChildren: () => import('./views/dashboard/dashboard.module').then((m) => m.DashboardModule)
      },
      {
        path: 'pages', loadChildren: () => import('./views/pages/pages.module').then((m) => m.PagesModule)
      },
      {
        path: 'foods', loadChildren: () => import('./views/foods/foods.module').then((m) => m.FoodsModule)
      },
      {
        path: 'users', loadChildren: () => import('./views/users/users.module').then((m) => m.UsersModule)
      },
      {
        path: 'datas', loadChildren: () => import('./views/data/data.module').then((m) => m.DataModule)
      },
    ]
  },
  {
    path: '404', component: Page404Component,
    data: {
      title: 'Page 404'
    }
  },
  {
    path: '500', component: Page500Component,
    data: {
      title: 'Page 500'
    }
  },
  {
    path: 'login', component: LoginComponent,
    data: {
      title: 'Login Page'
    }
  },
  {
    path: 'register', component: RegisterComponent,
    data: {
      title: 'Register Page'
    }
  },
  {path: '**', redirectTo: 'dashboard'}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {
      scrollPositionRestoration: 'top',
      anchorScrolling: 'enabled',
      initialNavigation: 'enabledBlocking'
      // relativeLinkResolution: 'legacy'
    })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
