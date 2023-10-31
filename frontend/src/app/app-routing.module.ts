import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {Page404Component} from './views/pages/page404/page404.component';
import {Page500Component} from './views/pages/page500/page500.component';
import {LoginComponent} from './views/pages/login/login.component';
import {AuthGuard} from "./auth/guards/auth.guard";

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full'},
  { path: 'dashboard', pathMatch: 'full', loadChildren: () => import('./views/dashboard/dashboard.module').then(m => m.DashboardModule), canActivate: [ AuthGuard] },
  { path: 'pages', pathMatch: 'full', loadChildren: () => import('./views/pages/pages.module').then(m => m.PagesModule), canActivate: [ AuthGuard] },
  { path: 'foods', pathMatch: 'full', loadChildren: () => import('./views/foods/foods.module').then(m => m.FoodsModule), canActivate: [ AuthGuard] },
  { path: 'users', pathMatch: 'full', loadChildren: () => import('./views/users/users.module').then(m => m.UsersModule), canActivate: [ AuthGuard] },
  { path: 'datas', pathMatch: 'full', loadChildren: () => import('./views/data/data.module').then(m => m.DataModule), canActivate: [ AuthGuard]  },
  { path: '404', component: Page404Component, data: {title: 'Page 404'} },
  { path: '500', component: Page500Component,data: {title: 'Page 500'} },
  { path: 'login', component: LoginComponent,data: {title: 'Login Page'} },
  // { path: 'register', component: RegisterComponent,data: {title: 'Register Page'} },
  { path: '**', redirectTo: 'dashboard'}
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
