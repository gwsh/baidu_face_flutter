#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html.
# Run `pod lib lint baidu_face_flutter.podspec' to validate before publishing.
#
Pod::Spec.new do |s|
  s.name             = 'baidu_face_flutter'
  s.version          = '0.0.1'
  s.summary          = 'A new Flutter project.'
  s.description      = <<-DESC
A new Flutter project.
                       DESC
  s.homepage         = 'http://example.com'
  s.license          = { :file => '../LICENSE' }
  s.author           = { 'Your Company' => 'email@example.com' }
  s.source           = { :path => '.' }
  s.source_files = 'Classes/**/*'
  s.public_header_files = ['Classes/**/*.h']
  s.dependency 'Flutter'
  s.platform = :ios, '8.0'

  s.static_framework = true
  s.vendored_frameworks = 'Vendors/*.framework'
  # ios system library
  s.libraries = [
        "c++"
  ]
  s.resources = 'Vendors/*.bundle'
  # Flutter.framework does not contain a i386 slice. Only x86_64 simulators are supported.
  s.pod_target_xcconfig = { 'DEFINES_MODULE' => 'YES', 'VALID_ARCHS[sdk=iphonesimulator*]' => 'x86_64' }
end
