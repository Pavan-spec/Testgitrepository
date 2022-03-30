multibranchPipelineJob('mr_builder_multibranch') {
    displayName('mr_builder_multibranch')
    branchSources {
        branchSource {
            source {
                gitlab {
                    //credentialsId("group-gitlab-dps-jenkins")
                    //repoOwner('')
                    repository('Testgitrepository')
                    repositoryUrl('https://github.com/Pavan-spec/Testgitrepository.git')

                    traits {
                        headWildcardFilter {
                            includes("decomp /* feature/* master/*")
                            excludes("develop")
                        }
                    }
                }
            }

            strategy {
                defaultBranchPropertyStrategy {
                    props {
                        buildRetentionBranchProperty {
                            buildDiscarder {
                                logRotator {
                                    daysToKeepStr("-20")
                                    numToKeepStr("20")
                                    artifactDaysToKeepStr("-20")
                                    artifactNumToKeepStr("-20")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    configure {
        def traits = it / sources / data / 'jenkins.branch.BranchSource' / source / traits
        traits << 'com.cloudbees.jenkins.plugins.gitlab.BranchDiscoveryTrait' {
            strategyId(3)
        }
    }
}
orphanedItemStrategy {
    discardOldItems {
        numToKeep(-1)
    }
}
factory {
    workflowBranchProjectFactory {
        scriptPath('jenkins/Jenkins-mr-builder')
    }
}

