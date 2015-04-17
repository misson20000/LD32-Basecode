#version 130
#define NUM_LIGHTS 16

uniform vec3  color[NUM_LIGHTS];
uniform float radius[NUM_LIGHTS];
uniform float spread[NUM_LIGHTS];
uniform vec2  pos[NUM_LIGHTS];
uniform vec4 ambient;

uniform sampler2D buf;

varying vec2 v_diffuseUV;

void main() {
  vec3 str = vec3(0);
  for(int i = 0; i < NUM_LIGHTS; i++) {
    if(radius[i] > 0.) {
      float dist = sqrt(pow(gl_FragCoord.x-pos[i].x, 2) + pow(gl_FragCoord.y-pos[i].y, 2));
      float d = (dist-spread[i])/radius[i];
      vec3 d3 = vec3(1)-d*color[i];
      str.r = max(str.r, d3.r);
      str.g = max(str.g, d3.g);
      str.b = max(str.b, d3.b);
    }
  }
  
  vec2 ts = textureSize(buf, 0);
  
  gl_FragColor = vec4(
    texture2D(buf, gl_FragCoord.xy/ts).rgb
    * max(str, ambient.rgb*ambient.a), 1.0);
}