#Steps for exec terraform:
#terraform init -backend-config "key=infraestructure/robotest/tfstate-${nameInstance}"
#terraform plan -var robot="MangoTest" o "test80" -var branch_robot=branches/RELEASE_1 -var id_instance="RELEASE_1_jorge.munoz"
#terraform apply -var robot="MangoTest" o "test80" -var branch_robot=branches/RELEASE_1 -var id_instance="RELEASE_1_jorge.munoz"


# param branch_robot: p.e. "RELEASE_1"
variable "branch_robot" {
  type = "string"
}

# param id_instance: p.e. "RELEASE_1_jorge.munoz"
variable "id_instance" {
  type = "string"
}

# param robot: "MangoTest" o "test80"
variable "robot" {
  type = "string"
}

terraform {
	backend "s3" {
		bucket = "mng-sysops"
		#key    = "infraestructure/robotest/tfstate-${var.robot}_${var.id_instance}" (variable interpolations are not allowed here. 
		#Whe move to: terraform init -backend-config "key=infraestructure/robotest/tfstate-${var.robot}_${var.id_instance}")
		access_key = "AKIAJGBDNWFXKAPCN4IQ"
		secret_key = "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"
		region = "eu-west-1"
	}
}

provider "aws" {
	access_key = "AKIAJGBDNWFXKAPCN4IQ"
	secret_key = "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"
	region     = "eu-west-1"
}

data "template_file" "userdata" {
  vars {
    admin_password = "12eple!x"
  }

  template = <<JSON
{
	"userdata": {
		"enable_rdp": true,
		"administrator_password": "$${admin_password}" 
	}
}
JSON
}

resource "aws_instance" "robotest" {
	# The connection block tells our provisioner how to 
	# communicate with the resource (instance)
	# La conexión con WinRM es muy lenta (3m), revisar https://blogs.msdn.microsoft.com/spatdsg/2014/01/20/timeouts-or-delays-connecting-to-winrm/
	connection {
		type     = "winrm"
		user     = "Administrator"
		password = "12eple!x"

		# set from default of 5m to 10m to avoid winrm timeout
		timeout = "10m"
	}	
	
    #provisioner "remote-exec" { 
	#	inline = [
    #        "mkdir C:\\MyFolder"
    #    ]
    #}	
	
    provisioner "file" {
        source      = "Provision_${var.robot}.ps1"
        destination = "C:\\Provision_${var.robot}.ps1"
    }

    provisioner "remote-exec" {
         inline = [
	     	"powershell.exe Set-ExecutionPolicy RemoteSigned -force",
           	"powershell.exe -version 4 -ExecutionPolicy Bypass -File C:\\Provision_${var.robot}.ps1 ${var.branch_robot}"
         ]
    }	
	
	#ami           = "ami-fc8cb885" (stable Windows_Server-2016-English-Full-Base)
	#ami           = "ami-fbd0e782"
	ami 		   = "ami-0864fcc37d7ff3d44"
	instance_type = "t2.medium"
	
	tags {
		Name = "${var.robot}_${var.id_instance}"
		ScheduledLifecycle = "true"
	}
	
	# Our Security group to allow WinRM access
	#security_groups = ["${aws_security_group.default.name}"]
	
	availability_zone = "eu-west-1b"
	subnet_id = "subnet-04415b72"
	vpc_security_group_ids = ["sg-9c1f3be4", "sg-38db9343", "sg-5a635620"]
}