import { User } from '../user';
import { UserService } from '../../services/user/user.service';
import { UserListComponent } from '../user-list/user-list.component';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, NavigationExtras } from '@angular/router';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss']
})
export class UserDetailsComponent implements OnInit {

  userId: number;
  user: User;

  constructor(private route: ActivatedRoute, private router: Router,
              private userService: UserService) { }

  ngOnInit(): void {
    this.user = new User();

    this.userId = this.route.snapshot.params['userId'];
    
    this.userService.getUser(this.userId)
      .subscribe(data => {
        console.log(data);
        this.user = data;
      }, error => console.log(error));
  }

  list(){
    this.router.navigate(['']);
  }

  updateUser(userId: number){
    this.router.navigate(['update', userId]);
  }

  createPhone(userId: number){
    const navigationExtras: NavigationExtras = {
      queryParams: {
        'userId': this.userId
      }
    };
    this.router.navigate(['add-phone', this.userId], navigationExtras);
  }
  phoneList(userId: number){
    const navigationExtras: NavigationExtras = {
      queryParams: {
        'userId': this.userId
      }
    };
    this.router.navigate(['/phones', this.userId], navigationExtras);
  }
}
