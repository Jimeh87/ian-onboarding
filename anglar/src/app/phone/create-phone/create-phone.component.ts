import { PhoneService } from '../../services/phone/phone.service';
import { Phone } from '../phone';
import { User } from '../../user/user';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, NavigationExtras } from '@angular/router';

@Component({
  selector: 'app-create-phone',
  templateUrl: './create-phone.component.html',
  styleUrls: ['./create-phone.component.scss']
})
export class CreatePhoneComponent implements OnInit {

  phone: Phone = new Phone();
  userId: number;
  submitted = false;

  constructor(private route: ActivatedRoute, private phoneService: PhoneService,
              private router: Router) { }

  ngOnInit(): void {
  }

  save(){
    this.route.queryParams.subscribe(params => {
      this.userId = params.userId;
    });
    this.phoneService
    .createPhone(this.phone, this.userId).subscribe(data => {
      console.log(data);
      this.phone = new Phone();
      this.gotoList();
    }), error => console.log(error);
  }

  newPhone(): void {
    this.submitted = false;
    this.phone = new Phone();
  }

  onSubmit(){
    this.submitted = true;
    if (!this.phone.primaryNumber){
      this.phone.primaryNumber = false;
    }
    this.save();
  }

  gotoList(){
    const navigationExtras: NavigationExtras = {
      queryParams: {
        userId: this.userId
      }
    };
    this.router.navigate(['/phones', this.userId], navigationExtras);
  }

}
