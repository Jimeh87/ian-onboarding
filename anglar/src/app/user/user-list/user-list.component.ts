import { UserDetailsComponent } from '../user-details/user-details.component';
import { Observable } from 'rxjs';
import { UserService } from '../../services/user/user.service';
import { User } from '../user';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {
  users: Observable<User[]>;

  constructor(private userService: UserService,
              private router: Router) { }

  ngOnInit(): void {
    this.reloadData();
  }
  reloadData() {
    this.users = this.userService.getUsersList();
  }

  deleteUser(userId: number) {
    this.userService.deleteUser(userId)
      .subscribe(
        data => {
          console.log(data);
          this.reloadData();
        },
        error => console.log(error));
  }

  userDetails(userId: number){
    this.router.navigate(['details', userId]);
  }

}
