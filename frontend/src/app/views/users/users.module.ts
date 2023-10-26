import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {
  ButtonGroupModule,
  ButtonModule,
  CardModule,
  CollapseModule,
  DropdownModule,
  FormModule,
  GridModule,
  NavbarModule,
  NavModule,
  SharedModule,
  UtilitiesModule
} from "@coreui/angular";
import {IconModule} from "@coreui/icons-angular";
import {ReactiveFormsModule} from "@angular/forms";
import {DocsComponentsModule} from "@docs-components/docs-components.module";
import {RouterModule, Routes} from "@angular/router";
import {DevExtremeModule} from "devextreme-angular";
import {UserProfileComponent} from "./user-profile/user-profile.component";
import {AuthUserComponent} from './auth-user/auth-user.component';
import {AuthRolComponent} from './auth-rol/auth-rol.component';
import {AuthGroupComponent} from './auth-group/auth-group.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'profile', component: UserProfileComponent,
        data: {
          title: 'Profile'
        }
      },
      {
        path: 'user', component: AuthUserComponent,
        data: {
          title: 'Users'
        }
      },
      {
        path: 'rol', component: AuthRolComponent,
        data: {
          title: 'Rols'
        }
      },
      {
        path: 'group', component: AuthGroupComponent,
        data: {
          title: 'Groups'
        }
      },
    ]
  }
];

@NgModule({
  declarations: [
    UserProfileComponent,
    AuthUserComponent,
    AuthRolComponent,
    AuthGroupComponent,
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    ButtonModule,
    ButtonGroupModule,
    GridModule,
    IconModule,
    CardModule,
    UtilitiesModule,
    DropdownModule,
    SharedModule,
    FormModule,
    ReactiveFormsModule,
    DocsComponentsModule,
    NavbarModule,
    CollapseModule,
    NavModule,
    DevExtremeModule,
  ]
})
export class UsersModule {
}
