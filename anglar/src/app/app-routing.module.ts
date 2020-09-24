import { UserDetailsComponent } from './user/user-details/user-details.component';
import { CreateUserComponent } from './user/create-user/create-user.component';
import { UserListComponent } from './user/user-list/user-list.component';
import { UpdateUserComponent } from './user/update-user/update-user.component';
import { CreatePhoneComponent } from './phone/create-phone/create-phone.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PhoneListComponent } from './phone/phone-list/phone-list.component';

const routes: Routes = [
  {
    path: '', component: UserListComponent
  },
  {
    path: 'add', component: CreateUserComponent
  },
  {
    path: 'update/:userId', component: UpdateUserComponent
  },
  {
    path: 'details/:userId', component: UserDetailsComponent
  },
  {
    path: 'add-phone/:userId', component: CreatePhoneComponent
  },
  {
    path: 'phones/:userId', component: PhoneListComponent
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
