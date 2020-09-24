import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerificationTwilioComponent } from './verification-twilio.component';

describe('VerificationTwilioComponent', () => {
  let component: VerificationTwilioComponent;
  let fixture: ComponentFixture<VerificationTwilioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VerificationTwilioComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VerificationTwilioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
