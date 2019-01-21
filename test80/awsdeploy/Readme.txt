# Generate AMI AWS mangomng with Packer
# Jenkins Job: https://cicd.sysops.mangodev.net/jenkins/job/robotest/job/generate-awsami-mangotest/
set AWS_ACCESS_KEY_ID=AKIAJGBDNWFXKAPCN4IQ
set AWS_SECRET_ACCESS_KEY=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
packer build RobotestPacker.json
#If the credentials are invalid perharps you'll need to create a new ones
#https://docs.aws.amazon.com/IAM/latest/UserGuide/id_credentials_access-keys.html#Using_CreateAccessKey

# Generate instance MangoTest with Terraform (previously modify RobotestTerraform.tf with the id of the image generated with packer)
# Parameters:
#    branch: p.e. "RELEASE-1"
#    sufixinstance: p.e. "jmunoz"
# Jenkisn Job for create instance: https://cicd.sysops.mangodev.net/jenkins/job/robotest/job/robotest-deploy-in-cloud/
# Jenkins Job for destroy instance: https://cicd.sysops.mangodev.net/jenkins/job/robotest/job/robotest-destroy-from-cloud/
docker run --rm -i -t \ 
        -e AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID} \ 
        -e AWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY} \ 
        -e AWS_DEFAULT_REGION=${AWS_DEFAULT_REGION} \ 
        -v $PWD:/terraform \ 
        -w /terraform \ 
        hashicorp/terraform:0.11.2 
