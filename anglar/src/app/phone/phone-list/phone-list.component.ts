import { Observable } from 'rxjs';
import { PhoneService } from '../../services/phone/phone.service';
import { Phone } from '../phone';
import { User } from '../../user/user'
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, NavigationExtras } from '@angular/router';

@Component({
  selector: 'app-phone-list',
  templateUrl: './phone-list.component.html',
  styleUrls: ['./phone-list.component.scss']
})
export class PhoneListComponent implements OnInit {
  phones: Observable<Phone[]>;
  userId: number;
  phoneId: number;

  constructor(private phoneService: PhoneService,
              private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.reloadData();

    this.userId = this.route.snapshot.params['userId'];
    this.phoneId = this.route.snapshot.params['phoneId'];
  }
  reloadData() {
    this.route.queryParams.subscribe(params => {
      this.userId = params['userId'];
    });
    this.phones = this.phoneService.getPhonesList(this.userId);
  }

  deletePhone(phoneId: number) {
    this.route.queryParams.subscribe(params => {
      this.userId = params['userId'];
    });
    this.phoneService.deletePhone(this.userId, phoneId)
      .subscribe(
        data => {
          console.log(data);
          this.reloadData();
        },
        error => console.log(error));
  }
}
